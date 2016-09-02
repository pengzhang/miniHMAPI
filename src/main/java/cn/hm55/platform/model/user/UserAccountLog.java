package cn.hm55.platform.model.user;

import java.util.Date;

public class UserAccountLog {
	
	private long uid;
	private String operation;   //操作记录
	private String op_type;     //操作类型:  0:积分, 1:余额
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
	public String getOp_type() {
		return op_type;
	}
	public void setOp_type(String op_type) {
		this.op_type = op_type;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
}
