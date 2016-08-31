package cn.hm55.platform.model;

public class User {
	
	private String username;
	private String password;
	private String slat;
	private int status;   //0:正常,1:删除(暂停),2:待审核,3:永久删除
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSlat() {
		return slat;
	}
	public void setSlat(String slat) {
		this.slat = slat;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
