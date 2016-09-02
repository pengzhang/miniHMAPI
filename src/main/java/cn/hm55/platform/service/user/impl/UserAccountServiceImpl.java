package cn.hm55.platform.service.user.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import cn.hm55.platform.exception.ServiceException;
import cn.hm55.platform.model.user.User;
import cn.hm55.platform.model.user.UserAccount;
import cn.hm55.platform.model.user.UserAccountLog;
import cn.hm55.platform.service.user.UserAccountService;
import cn.hm55.platform.service.user.UserService;

@Component
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private Sql2o sql2o;
	
	@Autowired
	private UserService userService;

	private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	private void countExecuteTime(long start, String name) {
		long end = System.currentTimeMillis();
		log.info("execute {} : {}ms", name, end - start);
	}

	@Override
	public long getUserScore(long uid) throws ServiceException {

		long start = System.currentTimeMillis();
		try (Connection conn = sql2o.open()) {
			String sql = "select uid, score from user_account where uid = :uid";
			UserAccount account = conn.createQuery(sql, sql).addParameter("uid", uid).executeAndFetchFirst(UserAccount.class);
			if(account == null){
				defaultUserAccount(uid);
				return 0;
			}
			this.countExecuteTime(start, "getUserScore");
			return account.getScore();
		} catch (ServiceException se) {
			throw new ServiceException(se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取用户积分失败");

		}
	}

	@Override
	public long getUserAccount(long uid) throws ServiceException {

		long start = System.currentTimeMillis();
		try (Connection conn = sql2o.open()) {
			String sql = "select uid, account from user_account where uid = :uid";
			UserAccount account = conn.createQuery(sql, sql).addParameter("uid", uid).executeAndFetchFirst(UserAccount.class);
			if(account == null){
				defaultUserAccount(uid);
				return 0;
			}
			this.countExecuteTime(start, "getUserAccount");
			return account.getAccount();
		} catch (ServiceException se) {
			throw new ServiceException(se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取用户账户余额失败");

		}
	}

	@Override
	public boolean modifyUserScore(long uid, long score, String cause) throws ServiceException {

		long start = System.currentTimeMillis();
		try (Connection conn = sql2o.beginTransaction()) {
			
			//查询当前积分
			String sql = "select uid, score from user_account where uid = :uid";
			UserAccount uscore = conn.createQuery(sql, sql).addParameter("uid", uid).executeAndFetchFirst(UserAccount.class);
			if(uscore == null){
				uscore = defaultUserAccount(uid);
			}
			// 当前账户积分少于消费积分
			if (score<0 && Math.abs(uscore.getScore()) < Math.abs(score)) {
				throw new ServiceException("积分不够");
			} else {
				// 更新积分
				String modifySql = "update user_account set score = :score where uid = :uid";
				conn.createQuery(modifySql, modifySql).addParameter("score", uscore.getScore() + score).addParameter("uid", uid).executeUpdate();

				// 积分消费日志
				String logSql = "insert into user_account_log (uid, operation, op_type, create_date) values (:uid, :operation, :op_type, :create_date)";
				conn.createQuery(logSql, logSql).addParameter("uid", uid).addParameter("operation", new String((cause + ",账户积分:" + (score > 0 ? "+" + score : score)).getBytes(), "utf-8")).addParameter("op_type", 0).addParameter("create_date", new Date()).executeUpdate();

			}
			conn.commit();
			this.countExecuteTime(start, "modifyUserScore");
			return true;
		} catch (ServiceException se) {
			throw new ServiceException(se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("修改用户账户积分失败");

		}
	}

	@Override
	public boolean modifyUserAccount(long uid, long account, String cause) throws ServiceException {
		
		long start = System.currentTimeMillis();
		try (Connection conn = sql2o.beginTransaction()) {
			
			//查询当前余额
			String sql = "select uid, account from user_account where uid = :uid";
			UserAccount uaccount = conn.createQuery(sql, sql).addParameter("uid", uid).executeAndFetchFirst(UserAccount.class);
			if(uaccount == null){
				uaccount = defaultUserAccount(uid);
			}
			// 当前账户余额少于消费余额
			if (account < 0 && Math.abs(uaccount.getAccount()) < Math.abs(account)) {
				throw new ServiceException("余额不够");
			} else {
				// 更新余额
				String modifySql = "update user_account set account = :account where uid = :uid";
				conn.createQuery(modifySql, modifySql).addParameter("account", uaccount.getAccount() + account).addParameter("uid", uid).executeUpdate();

				// 余额消费日志
				String logSql = "insert into user_account_log (uid, operation, op_type, create_date) values (:uid, :operation, :op_type, :create_date)";
				conn.createQuery(logSql, logSql).addParameter("uid", uid).addParameter("operation", new String((cause + ",账户余额:" + (account > 0 ? "+" + account : account)).getBytes(), "utf-8")).addParameter("op_type", 1).addParameter("create_date", new Date()).executeUpdate();

			}
			conn.commit();
			this.countExecuteTime(start, "modifyUserAccount");
			return true;
		} catch (ServiceException se) {
			throw new ServiceException(se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("修改用户账户余额失败");

		}
	}

	@Override
	public List<UserAccountLog> getUserAccountLog(long uid, int op_type) throws ServiceException {
		
		long start = System.currentTimeMillis();
		try (Connection conn = sql2o.open()) {
			String sql = "select * from user_account_log where uid = :uid and op_type = :op_type order by create_date desc";
			List<UserAccountLog> userAccountLogs = conn.createQuery(sql, sql).addParameter("uid", uid).addParameter("op_type", op_type).executeAndFetch(UserAccountLog.class);
			this.countExecuteTime(start, "getUserAccount");
			return userAccountLogs;
		} catch (ServiceException se) {
			throw new ServiceException(se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取用户账户操作失败");

		}
	}
	
	private UserAccount defaultUserAccount(long uid) throws ServiceException{
		try (Connection conn = sql2o.beginTransaction()){
			User user = userService.getUser(uid);
			if(user == null){
				throw new ServiceException("用户不存在,没有该账户信息");
			}
			String sql = "insert into user_account (uid, score, account) values (:uid, 0, 0)";
			conn.createQuery(sql, sql).addParameter("uid", uid).executeUpdate().commit();
			return new UserAccount(uid, 0, 0);
		} catch (ServiceException se) {
			throw new ServiceException(se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("创建用户账户失败");
		}
	}

}
