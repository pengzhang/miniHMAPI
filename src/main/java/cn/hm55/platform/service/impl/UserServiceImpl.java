package cn.hm55.platform.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import cn.hm55.platform.exception.ServiceException;
import cn.hm55.platform.model.user.User;
import cn.hm55.platform.service.UserService;
import cn.hm55.platform.util.cache.Cache;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private Sql2o sql2o;

	@Autowired
	private Cache cache;

	private String user_info = "id, username, status";

	private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private void countExecuteTime(long start, String name) {
		long end = System.currentTimeMillis();
		log.info("execute {} : {}ms", name, end - start);
	}

	@Override
	public boolean register(String username, String password) throws ServiceException {

		long start = System.currentTimeMillis();
		// 检查用户名
		if (getUserByUsername(username) != null) {
			throw new ServiceException("用户名已存在");
		}

		String salt = RandomStringUtils.randomAlphanumeric(5);
		String sql = "insert into user (username, password, salt, create_date, update_date) values (:username, :password, :salt, :create_date, :update_date)";
		try (Connection conn = sql2o.open()) {
			conn.createQuery(sql, sql).addParameter("username", username).addParameter("password", DigestUtils.md5Hex(password + salt)).addParameter("salt", salt)
					.addParameter("create_date", new Date()).addParameter("update_date", new Date()).executeUpdate().commit();
			this.countExecuteTime(start, "register");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("注册失败");
		}
	}

	@Override
	public boolean login(String username, String password) throws ServiceException {

		long start = System.currentTimeMillis();
		User user = getUserByUsername(username);
		if (user == null || user.getStatus() != 0) {
			throw new ServiceException("用户不存在或用户已被删除");
		} else {
			// 判断密码是否相同
			if (DigestUtils.md5Hex(password + user.getSalt()).equals(user.getPassword())) {
				// TODO 用户登录日志
				this.countExecuteTime(start, "login");
				return true;
			} else {
				throw new ServiceException("密码错误");
			}
		}

	}

	@Override
	public boolean modifyPassword(long userid, String password) throws ServiceException {

		long start = System.currentTimeMillis();
		User user = getUser(userid);
		if (user == null) {
			throw new ServiceException("用户不存在");
		} else {
			// 重新生成密码salt
			String salt = RandomStringUtils.randomAlphanumeric(5);
			String sql = "update user set password=:password, salt=:salt, update_date=:update_date where id=:id";
			try (Connection conn = sql2o.open()) {
				conn.createQuery(sql, sql).addParameter("password", DigestUtils.md5Hex(password + salt)).addParameter("salt", salt).addParameter("update_date", new Date()).addParameter("id", userid)
						.executeUpdate().commit();
				this.countExecuteTime(start, "modifyPassword");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("修改用户密码失败");
			}
		}
	}

	@Override
	public User getUser(long userid) throws ServiceException {

		long start = System.currentTimeMillis();
		String sql = "select " + user_info + " from user where id=:id";
		try (Connection conn = sql2o.open()) {
			User user = conn.createQuery(sql, sql).addParameter("id", userid).executeAndFetchFirst(User.class);
			this.countExecuteTime(start, "getUser");
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("通过用户ID获取用户失败");
		}
	}

	@Override
	public User getUserByUsername(String username) throws ServiceException {

		long start = System.currentTimeMillis();
		String sql = "select " + user_info + " from user where username=:username";
		try (Connection conn = sql2o.open()) {
			User user = conn.createQuery(sql, sql).addParameter("username", username).executeAndFetchFirst(User.class);
			this.countExecuteTime(start, "getUserByUsername");
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("通过用户名获取用户失败");
		}
	}

	@Override
	public List<User> getUsers(int page, int size) throws ServiceException {

		long start = System.currentTimeMillis();
		String sql = "select " + user_info + " from user where status=0 limit :page, :size";
		try (Connection conn = sql2o.open()) {
			List<User> users = conn.createQuery(sql, sql).addParameter("page", (page - 1) * size).addParameter("size", size).executeAndFetch(User.class);
			this.countExecuteTime(start, "getUsers");
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取用户列表失败");
		}
	}

	@Override
	public List<User> getAllUsers(int page, int size) throws ServiceException {

		long start = System.currentTimeMillis();
		String sql = "select " + user_info + " from user limit :page, :size";
		try (Connection conn = sql2o.open()) {
			List<User> users = conn.createQuery(sql, sql).addParameter("page", (page - 1) * size).addParameter("size", size).executeAndFetch(User.class);
			this.countExecuteTime(start, "getAllUsers");
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取所有用户列表失败");
		}
	}

	@Override
	public List<User> searchUsername(String username, int page, int size) {

		long start = System.currentTimeMillis();
		String sql = "select " + user_info + " from user where username like :username and status=0 limit :page, :size";
		try (Connection conn = sql2o.open()) {
			List<User> users = conn.createQuery(sql, sql).addParameter("username", "%" + username + "%").addParameter("page", (page - 1) * size).addParameter("size", size).executeAndFetch(User.class);
			this.countExecuteTime(start, "searchUsername");
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取所有用户列表失败");
		}
	}

	@Override
	public long getUserTotal() throws ServiceException {

		long start = System.currentTimeMillis();
		String total = cache.get("_user_total");
		// 如果缓存中没有数据,从数据库中获取
		if (total == null) {
			try (Connection conn = sql2o.open()) {
				String sql = "select count(*) from user";
				long count = conn.createQuery(sql, sql).executeScalar(Long.class);
				this.countExecuteTime(start, "getUserTotalFromDB");
				return count;
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("获取用户总数");
			}
		} else {
			// 缓存中的数据转换错误,返回0
			try {
				this.countExecuteTime(start, "getUserTotalFromCache");
				return Long.parseLong(total);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}

		}
	}

}
