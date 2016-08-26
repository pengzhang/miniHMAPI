package cn.hm55.platform.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.hm55.platform.service.UserService;
import cn.hm55.platform.util.Json;

@Path("/user/1.1")
@Component
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @api {get} /user/1.1/info/{page} 获取用户信息
	 * @apiGroup User
	 * @apiParam {String} page 页数
	 * @apiVersion 1.2.3
	 * @param page
	 * @return
	 */
	@GET
	@Path("/info/{page}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserInfo(@PathParam("page") int page) {
		return Json.toJson(userService.getUsers(page)).toString();
	}

}
