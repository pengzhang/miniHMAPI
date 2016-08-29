package cn.hm55.platform.service;

import java.util.List;

import cn.hm55.platform.model.User;

public interface UserService {

	public boolean register(String username, String password);
	
	public String login(String username, String password);
	
	public boolean modifyPassword(long userid, String password);
	
	public User getUser(long userid);
	
	public User getUserByUsername(String username);
	
	public List<User> getUsers(int page, int size);
	
	public List<User> searchUsername(String username, int page, int size);
	
	
}
