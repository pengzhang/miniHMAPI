package cn.hm55.platform.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 * @author zhangpeng
 *
 */
public class Config {

	public static String get(String key) {
		Properties props = new Properties();
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("/application.properties");
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
