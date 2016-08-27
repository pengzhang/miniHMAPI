package cn.hm55.platform.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * 发送短信
 * @author mtedu
 *
 */
@Component
public class Sms {

	private Log log = LogFactory.getLog(Sms.class);

	/**
	 * 群发短信
	 * @param mobiles
	 * @param content
	 */
	public void send(List<String> mobiles, String content) {
		for (String mobile : mobiles) {
			send(mobile, content);
		}
	}

	/**
	 * 单独发送
	 * @param mobile
	 * @param content
	 */
	public void send(String mobile, String content) {
		log.info("send sms to:" + mobile + ", content:" + content);
	}
}
