package cn.hm55.platform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import cn.hm55.platform.service.UserService;
import cn.hm55.platform.util.ObjectUtil;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private Sql2o sql2o;

	@Override
	public List<Map<String, Object>> getUsers(int page) {
		try (Connection conn = sql2o.open()) {
			String sql = "select * from mtedu_community_user limit " + page + ",10";
			List<Map<String, Object>> list = conn.createQuery(sql).executeAndFetchTable().asList();
			return ObjectUtil.toListMap(list);

		}
	}

}
