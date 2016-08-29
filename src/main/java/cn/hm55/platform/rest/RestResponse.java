package cn.hm55.platform.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL) 
public class RestResponse {
	
	private int status;
	private Object data;
	private String message;
	
	public RestResponse() {
	}
	
	public RestResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public RestResponse(int status, Object data, String message) {
		this.status = status;
		this.data = data;
		this.message = message;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
