package cn.hm55.platform.rest.user;

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
import cn.hm55.platform.model.user.UserAccount;
import cn.hm55.platform.model.user.UserAccountLog;
import cn.hm55.platform.rest.RestResponse;
import cn.hm55.platform.service.user.UserAccountService;


@Path("/user/account")
@Component
public class UserAccountController {

	@Autowired
	private UserAccountService userAccountService;
	
	
	/**
	 * @api {get} /api/user/account/score/{uid} 获取用户账户积分
	 * @apiGroup UserAccount
	 * @apiParam {long} uid 用户ID
	 * @param uid
	 * @return
	 * @throws JerseyException
	 */
	@GET
	@Path("/score/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public RestResponse getUserScore(@PathParam("uid") long uid) throws JerseyException {
		try {
			System.out.println("===========" + uid);
			long score = userAccountService.getUserScore(uid);
			return new RestResponse(1, score, "获取用户积分成功");
		} catch (Exception e) {
			throw new JerseyException(-1, e.getMessage());
		} catch(Throwable t){
			t.printStackTrace();
			throw new JerseyException(-1, "获取用户积分失败");
		}
	}
	
	/**
	 * @api {get} /api/user/account/money/{uid} 获取用户账户余额
	 * @apiGroup UserAccount
	 * @apiParam {long} uid 用户ID
	 * @param uid
	 * @return
	 * @throws JerseyException
	 */
	@GET
	@Path("/money/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public RestResponse getUserMoney(@PathParam("uid") long uid) throws JerseyException {
		try {
			long money = userAccountService.getUserAccount(uid);
			return new RestResponse(1, money, "获取用户余额成功");
		} catch (Exception e) {
			throw new JerseyException(-1, e.getMessage());
		} catch(Throwable t){
			t.printStackTrace();
			throw new JerseyException(-1, "获取用户余额失败");
		}
	}
	
	
	/**
	 * @api {post} /api/user/account/modify/score 修改用户积分
	 * @apiGroup UserAccount
	 * @apiParam {long} uid 用户ID
	 * @apiParam {long} score 积分
	 * @apiParam {String} cause 消费说明
	 * @param account
	 * @return
	 * @throws JerseyException
	 */
	@POST
	@Path("/modify/score")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RestResponse modifyUserScore(UserAccount account) throws JerseyException {
		try {
			userAccountService.modifyUserScore(account.getUid(), account.getScore(), account.getCause());
			return new RestResponse(1, "修改用户积分成功");
		} catch (Exception e) {
			throw new JerseyException(-1, e.getMessage());
		} catch(Throwable t){
			t.printStackTrace();
			throw new JerseyException(-1, "修改用户积分失败");
		}
	}
	
	/**
	 * @api {post} /api/user/account/modify/money 修改用户余额
	 * @apiGroup UserAccount
	 * @apiParam {long} uid 用户ID
	 * @apiParam {long} account 金额
	 * @apiParam {String} cause 消费说明
	 * @param account
	 * @return
	 * @throws JerseyException
	 */
	@POST
	@Path("/modify/money")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RestResponse modifyUserMoney(UserAccount account) throws JerseyException {
		try {
			userAccountService.modifyUserAccount(account.getUid(), account.getAccount(), account.getCause());
			return new RestResponse(1, "修改用户余额成功");
		} catch (Exception e) {
			throw new JerseyException(-1, e.getMessage());
		} catch(Throwable t){
			t.printStackTrace();
			throw new JerseyException(-1, "修改用户余额失败");
		}
	}
	
	/**
	 * @api {get} /api/user/account/log/score/{uid} 获取用户账户积分消费日志
	 * @apiGroup UserAccount
	 * @apiParam {long} uid 用户ID
	 * @param uid
	 * @return
	 * @throws JerseyException
	 */
	@GET
	@Path("/log/score/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public RestResponse getUserScoreLog(@PathParam("uid") long uid) throws JerseyException {
		try {
			List<UserAccountLog> logs = userAccountService.getUserAccountLog(uid, 0);
			return new RestResponse(1, logs, "获取用户积分消费日志成功");
		} catch (Exception e) {
			throw new JerseyException(-1, e.getMessage());
		} catch(Throwable t){
			t.printStackTrace();
			throw new JerseyException(-1, "获取用户积分消费日志失败");
		}
	}
	
	/**
	 * @api {get} /api/user/account/log/money/{uid} 获取用户账户余额消费日志
	 * @apiGroup UserAccount
	 * @apiParam {long} uid 用户ID
	 * @param uid
	 * @return
	 * @throws JerseyException
	 */
	@GET
	@Path("/log/money/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public RestResponse getUserMoneyLog(@PathParam("uid") long uid) throws JerseyException {
		try {
			List<UserAccountLog> logs = userAccountService.getUserAccountLog(uid, 1);
			return new RestResponse(1, logs, "获取用户余额消费日志成功");
		} catch (Exception e) {
			throw new JerseyException(-1, e.getMessage());
		} catch(Throwable t){
			t.printStackTrace();
			throw new JerseyException(-1, "获取用户余额消费日志失败");
		}
	}
	
}
