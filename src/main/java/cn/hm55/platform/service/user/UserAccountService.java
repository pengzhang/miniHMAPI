package cn.hm55.platform.service.user;

import java.util.List;

import cn.hm55.platform.exception.ServiceException;
import cn.hm55.platform.model.user.UserAccountLog;

public interface UserAccountService {
	
	/**
	 * 获取积分
	 * @param uid
	 * @return
	 * @throws ServiceException
	 */
	public long getUserScore(long uid) throws ServiceException;
	
	/**
	 * 获取金额账户
	 * @param uid
	 * @return
	 * @throws ServiceException
	 */
	public long getUserAccount(long uid) throws ServiceException;
	
	/**
	 * 修改积分
	 * @param uid
	 * @param score
	 * @return
	 * @throws ServiceException
	 */
	public boolean modifyUserScore(long uid, long score, String cause) throws ServiceException;
	
	/**
	 * 修改账户金额
	 * @param uid
	 * @param account
	 * @return
	 * @throws ServiceException
	 */
	public boolean modifyUserAccount(long uid, long account, String cause) throws ServiceException; 
	
	/**
	 * 用户账户和积分操作记录
	 * @param uid
	 * @param op_type
	 * @return
	 * @throws ServiceException
	 */
	public List<UserAccountLog> getUserAccountLog(long uid, int op_type) throws ServiceException;

}
