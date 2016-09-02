package cn.hm55.platform.service.user;

import java.util.List;

import cn.hm55.platform.exception.ServiceException;
import cn.hm55.platform.model.user.User;

public interface UserService {

	/**
	 * 用户注册
	 * @param username
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public boolean register(String username, String password) throws ServiceException;
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public boolean login(String username, String password) throws ServiceException;
	
	/**
	 * 修改密码
	 * @param userid
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public boolean modifyPassword(long userid, String password) throws ServiceException;
	
	/**
	 * 获取用户信息
	 * @param userid
	 * @return
	 * @throws ServiceException
	 */
	public User getUser(long userid) throws ServiceException;
	
	/**
	 * 通过用户名获取用户信息
	 * @param username
	 * @return
	 * @throws ServiceException
	 */
	public User getUserByUsername(String username) throws ServiceException;
	
	/**
	 * 获取全部正常的用户列表
	 * @param page
	 * @param size
	 * @return
	 * @throws ServiceException
	 */
	public List<User> getUsers(int page, int size) throws ServiceException;
	
	/**
	 * 获取全部用户列表
	 * @param page
	 * @param size
	 * @return
	 * @throws ServiceException
	 */
	public List<User> getAllUsers(int page, int size) throws ServiceException;
	
	/**
	 * 搜索用户名
	 * @param username
	 * @param page
	 * @param size
	 * @return
	 * @throws ServiceException
	 */
	public List<User> searchUsername(String username, int page, int size) throws ServiceException;
	
	/**
	 * 用户总数
	 * @return
	 * @throws ServiceException
	 */
	public long getUserTotal() throws ServiceException;
}
