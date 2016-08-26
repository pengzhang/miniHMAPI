package cn.hm55.platform.util;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * HttpClient 提供GET,POST,DELETE,PUT方法的访问
 * 基于Jersey Client
 * @author zhangpeng
 *
 */
public class RestClient {
	static ClientConfig config = new DefaultClientConfig();
	static Client client = Client.create(config);

	public static String get(String url) {
		WebResource webResource = client.resource(url);
		return webResource.type(MediaType.APPLICATION_JSON_TYPE).get(String.class);
	}

	public static String delete(String url) {
		WebResource webResource = client.resource(url);
		return webResource.type(MediaType.APPLICATION_JSON_TYPE).delete(String.class);
	}

	public static String put(String url,String obj) {
		WebResource webResource = client.resource(url);
		return webResource.type(MediaType.APPLICATION_JSON_TYPE).put(String.class, obj);
	}

	public static String post(String url,String obj) {
		WebResource webResource = client.resource(url);
		return webResource.type(MediaType.APPLICATION_JSON_TYPE).post(String.class, obj);
	}
}
