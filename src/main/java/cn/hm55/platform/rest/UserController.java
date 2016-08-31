package cn.hm55.platform.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.hm55.platform.exception.JerseyException;
import cn.hm55.platform.exception.ServiceException;
import cn.hm55.platform.model.User;
import cn.hm55.platform.service.UserService;
import cn.hm55.platform.util.cache.Cache;

@Path("/user")
@Component
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private Cache cache;

	/**
	 * @api {post} /api/user/register 用户注册
	 * @apiGroup User
	 * @apiParam {String} username 用户名
	 * @apiParam {String} password 密码
	 * @apiVersion 0.1
	 * @return
	 */
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RestResponse register(User user) throws JerseyException {

		try {
			boolean flag = userService.register(user.getUsername(), user.getPassword());
			if (flag) {
				// 注册成功,缓存计用户总数
				cache.incr("_user_total");
			}
			return new RestResponse(1, "注册成功");
		} catch (ServiceException se) {
			throw new JerseyException(-1, se.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
			throw new JerseyException(-1, "注册失败");
		}
	}

	/**
	 * @api {post} /api/user/login 用户登录
	 * @apiGroup User
	 * @apiParam {String} username 用户名
	 * @apiParam {String} password 密码
	 * @apiVersion 0.1
	 * @return
	 */
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RestResponse login(User user) throws JerseyException {
		try {
			userService.login(user.getUsername(), user.getPassword());
			return new RestResponse(1, "登录成功");
		} catch (ServiceException se) {
			throw new JerseyException(-1, se.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
			throw new JerseyException(-1, "登录失败");
		}
	}

}
