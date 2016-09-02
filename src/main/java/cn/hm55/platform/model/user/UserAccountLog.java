package cn.hm55.platform.model.user;

import java.util.Date;

public class UserAccountLog {
	
	private long uid;
	private String operation;   //操作记录
	private Date create_date;    //操作日期
	
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
}
