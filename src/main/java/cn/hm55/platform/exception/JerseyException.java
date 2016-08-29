package cn.hm55.platform.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import cn.hm55.platform.rest.RestResponse;

/**
 * Jersey 异常处理
 * 
 * @author zp
 */
public class JerseyException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	public JerseyException(RestResponse error) {
		super(Response.serverError().entity(error).status(200).build());
	}
	
	public JerseyException(int status, String message) {
		super(Response.serverError().entity(getErrorBean(status, message)).status(200).build());
	}
	
	public static RestResponse getErrorBean(int status, String message) {
		RestResponse errorBean = new RestResponse(status,message);
		return errorBean;
	}

}

