package cn.hm55.platform.service;

import java.util.List;

import cn.hm55.platform.exception.ServiceException;
import cn.hm55.platform.model.user.User;

public interface UserService {

	public boolean register(String username, String password) throws ServiceException;
	
	public boolean login(String username, String password) throws ServiceException;
	
	public boolean modifyPassword(long userid, String password) throws ServiceException;
	
	public User getUser(long userid) throws ServiceException;
	
	public User getUserByUsername(String username) throws ServiceException;
	
	public List<User> getUsers(int page, int size) throws ServiceException;
	
	public List<User> getAllUsers(int page, int size) throws ServiceException;
	
	public List<User> searchUsername(String username, int page, int size) throws ServiceException;
	
	public long getUserTotal() throws ServiceException;
}
