package cn.hm55.platform.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	 * @apiVersion 0.1.0
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
	 * @apiVersion 0.1.0
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
	
	/**
	 * @api {post} /api/user/modify/password 修改密码
	 * @apiGroup User
	 * @apiParam {String} id 用户ID
	 * @apiParam {String} password 密码
	 * @apiVersion 0.1.0
	 * @return
	 */
	@POST
	@Path("/modify/password")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RestResponse modifyPassword(User user) throws JerseyException {
		try {
			userService.modifyPassword(user.getId(), user.getPassword());
			return new RestResponse(1, "修改密码成功");
		} catch (ServiceException se) {
			throw new JerseyException(-1, se.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
			throw new JerseyException(-1, "修改密码失败");
		}
	}
	
	/**
	 * @api {get} /api/user/{id} 根据ID获取用户信息
	 * @apiGroup User
	 * @apiParam {String} id 用户ID
	 * @apiVersion 0.1.0
	 * @return
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RestResponse getUser(@PathParam("id") long id) throws JerseyException {
		try {
			User user = userService.getUser(id);
			return new RestResponse(1, user, "获取用户信息成功");
		} catch (ServiceException se) {
			throw new JerseyException(-1, se.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
			throw new JerseyException(-1, "获取用户信息失败");
		}
	}
	
	/**
	 * @api {get} /api/user/username/{username} 根据用户名获取用户信息
	 * @apiGroup User
	 * @apiParam {String} username 用户名
	 * @apiVersion 0.1.0
	 * @return
	 */
	@GET
	@Path("/username/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RestResponse getUserByUsername(@PathParam("username") String username) throws JerseyException {
		try {
			User user = userService.getUserByUsername(username);
			return new RestResponse(1, user, "获取用户信息成功");
		} catch (ServiceException se) {
			throw new JerseyException(-1, se.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
			throw new JerseyException(-1, "获取用户信息失败");
		}
	}
	
	/**
	 * @api {get} /api/user/list/{page}/{size} 获取正常用户信息列表
	 * @apiGroup User
	 * @apiParam {String} page 页数
	 * @apiParam {String} size 条数
	 * @apiVersion 0.1.0
	 * @return
	 */
	@GET
	@Path("/list/{page}/{size}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RestResponse getUsers(@PathParam("page") int page, @PathParam("size") int size) throws JerseyException {
		try {
			List<User> users = userService.getUsers(page, size);
			return new RestResponse(1, users, "获取用户信息成功");
		} catch (ServiceException se) {
			throw new JerseyException(-1, se.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
			throw new JerseyException(-1, "获取用户信息失败");
		}
	}
	
	/**
	 * @api {get} /api/user/list/all/{page}/{size} 获取全部用户信息列表
	 * @apiGroup User
	 * @apiParam {String} page 页数
	 * @apiParam {String} size 条数
	 * @apiVersion 0.1.0
	 * @return
	 */
	@GET
	@Path("/list/all/{page}/{size}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RestResponse getAllUsers(@PathParam("page") int page, @PathParam("size") int size) throws JerseyException {
		try {
			List<User> users = userService.getUsers(page, size);
			return new RestResponse(1, users, "获取用户信息成功");
		} catch (ServiceException se) {
			throw new JerseyException(-1, se.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
			throw new JerseyException(-1, "获取用户信息失败");
		}
	}
	
	/**
	 * @api {post} /api/user/search/username/{page}/{size} 搜索用户名
	 * @apiGroup User
	 * @apiParam {String} username 用户名 body
	 * @apiParam {String} page 页数
	 * @apiParam {String} size 条数
	 * @apiVersion 0.1.0
	 * @return
	 */
	@POST
	@Path("/search/username/{page}/{size}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RestResponse searchUsername(@PathParam("page") int page, @PathParam("size") int size, User user) throws JerseyException {
		try {
			List<User> users = userService.searchUsername(user.getUsername(), page, size);
			return new RestResponse(1, users, "搜索用户名成功");
		} catch (ServiceException se) {
			throw new JerseyException(-1, se.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
			throw new JerseyException(-1, "搜索用户名失败");
		}
	}
	
	/**
	 * @api {get} /api/user/total 获取用户总数
	 * @apiGroup User
	 * @apiVersion 0.1.0
	 * @return
	 */
	@GET
	@Path("/total")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RestResponse getUserTotal() throws JerseyException {
		try {
			long total= userService.getUserTotal();
			return new RestResponse(1, total, "获取用户总数成功");
		} catch (ServiceException se) {
			throw new JerseyException(-1, se.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
			throw new JerseyException(-1, "获取用户总数失败");
		}
	}

}
