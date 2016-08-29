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

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private Sql2o sql2o;

	@Override
	public boolean register(String username, String password) throws ServiceException{
		
		String salt = RandomStringUtils.randomAlphanumeric(5);
		String sql = "insert into user (username, password, salt) values (:username, :password, :salt)";
		try (Connection conn = sql2o.open()) {
			conn.createQuery(sql)
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
	public String login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean modifyPassword(long userid, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUser(long userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsers(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> searchUsername(String username, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

}
