package cn.hm55.platform.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ApiCloud推送
 * 
 * @author mtedu
 * 
 */

@Component
public class ApiCloudPush {
	
	private Logger log = LoggerFactory.getLogger(ApiCloudPush.class);

	/**
	 * 发送到IOS
	 * @param username
	 * @param content
	 */
	public void sendIos(String username, String content) {
		log.info("send push to ios");
	}

	/**
	 * 发送到Android
	 * @param username
	 * @param content
	 */
	public void sendAndroid(String username, String content) {
		log.info("send push to android");
	}

	/**
	 * 发送到Android和IOS
	 * @param username
	 * @param content
	 */
	public void sendAll(String username, String content) {
		sendIos(username, content);
		sendAndroid(username, content);
	}
}
