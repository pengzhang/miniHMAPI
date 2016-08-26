package cn.hm55.platform.service;

import java.util.List;
import java.util.Map;

public interface UserService {

	public List<Map<String,Object>> getUsers(int page);
}
