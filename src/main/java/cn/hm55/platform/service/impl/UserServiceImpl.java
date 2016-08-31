package cn.hm55.platform.service.impl;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import cn.hm55.platform.exception.ServiceException;
import cn.hm55.platform.model.User;
import cn.hm55.platform.service.UserService;
import cn.hm55.platform.util.cache.Cache;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private Sql2o sql2o;
	
	@Autowired
	private Cache cache;

	@Override
	public boolean register(String username, String password) throws ServiceException{
		//检查用户名
		if(getUserByUsername(username) != null){
			throw new ServiceException("用户名已存在");
		}
		
		String salt = RandomStringUtils.randomAlphanumeric(5);
		String sql = "insert into user (username, password, salt) values (:username, :password, :salt)";
		try (Connection conn = sql2o.open()) {
			conn.createQuery(sql,sql)
			.addParameter("username", username)
			.addParameter("password", DigestUtils.md5Hex(password + salt))
			.addParameter("salt", salt)
			.executeUpdate().commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("注册失败");
		}
	}

	@Override
	public boolean login(String username, String password) throws ServiceException {
		User user = getUserByUsername(username);
		if(user == null || user.getStatus()!=0){
			throw new ServiceException("用户不存在或用户已被删除");
		}else{
			//判断密码是否相同
			if(DigestUtils.md5Hex(password + user.getSlat()).equals(user.getPassword())){
				return true;
			}else{
				throw new ServiceException("密码错误");
			}
		}
	}

	@Override
	public boolean modifyPassword(long userid, String password) throws ServiceException{
		User user = getUser(userid);
		if(user == null){
			throw new ServiceException("用户不存在");
		}else{
			//重新生成密码salt
			String salt = RandomStringUtils.randomAlphanumeric(5);
			String sql = "update use set password=:password, salt=:salt where id=:userid";
			try (Connection conn = sql2o.open()){
				conn.createQuery(sql, sql)
				.addParameter("password", DigestUtils.md5Hex(password + salt))
				.addParameter("salt", salt)
				.addParameter("id", userid)
				.executeUpdate().commit();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("修改用户密码失败");
			}
		}
	}

	@Override
	public User getUser(long userid) throws ServiceException{
		String sql = "select * from user where id=:id";
		try (Connection conn = sql2o.open()){
			return conn.createQuery(sql, sql)
					.addParameter("id", userid)
					.executeAndFetchFirst(User.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("通过用户ID获取用户失败");
		}
	}

	@Override
	public User getUserByUsername(String username) throws ServiceException{
		String sql = "select * from user where username=:username";
		try(Connection conn = sql2o.open()){
			return conn.createQuery(sql, sql)
			.addParameter("username", username)
			.executeAndFetchFirst(User.class);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("通过用户名获取用户失败");
		}
	}

	@Override
	public List<User> getUsers(int page, int size) throws ServiceException {
		String sql = "select * from user where status=0 limit :page, :size";
		try (Connection conn = sql2o.open()){
			return conn.createQuery(sql, sql)
				.addParameter("page", (page-1)*size)
				.addParameter("size", size)
				.executeAndFetch(User.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取用户列表失败");
		}
	}
	
	@Override
	public List<User> getAllUsers(int page, int size) throws ServiceException {
		String sql = "select * from user limit :page, :size";
		try (Connection conn = sql2o.open()){
			return conn.createQuery(sql, sql)
				.addParameter("page", (page-1)*size)
				.addParameter("size", size)
				.executeAndFetch(User.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取所有用户列表失败");
		}
	}

	@Override
	public List<User> searchUsername(String username, int page, int size) {
		String sql = "select * from user where username like '%:username%' and status=0 limit :page, :size";
		try (Connection conn = sql2o.open()){
			return conn.createQuery(sql, sql)
				.addParameter("username", username)
				.addParameter("page", (page-1)*size)
				.addParameter("size", size)
				.executeAndFetch(User.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取所有用户列表失败");
		}
	}

	@Override
	public long getUserTotal() throws ServiceException{
		String total = cache.get("_user_total");
		//如果缓存中没有数据,从数据库中获取
		if(total == null){
			try (Connection conn = sql2o.open()){
				String sql = "select count(*) from user";
				return conn.createQuery(sql, sql).executeScalar(Long.class);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("获取用户总数");
			}
		}else{
			//缓存中的数据转换错误,返回0
			try {
				return Long.parseLong(total);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			
		}
	}



}
