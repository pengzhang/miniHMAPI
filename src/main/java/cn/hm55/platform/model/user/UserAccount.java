package cn.hm55.platform.model.user;


/**
 * 用户账户和积分
 * @author mtedu
 *
 */
public class UserAccount {
	
	private long uid;
	private long score;  //积分
	private long account; //账户金额
	
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	public long getAccount() {
		return account;
	}
	public void setAccount(long account) {
		this.account = account;
	}
	
}