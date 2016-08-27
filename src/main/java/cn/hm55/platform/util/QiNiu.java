package cn.hm55.platform.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 七牛云存储
 * @author mtedu
 *
 */

@Component
public class QiNiu {
	
	private Log log = LogFactory.getLog(QiNiu.class);

	Auth auth = Auth.create(Config.get("qiniu.access_key"), Config.get("qiniu.secret_key"));
	UploadManager uploadManager = new UploadManager();

	public String getUpToken() {
		return auth.uploadToken(Config.get("qiniu.bucketname"));
	}

	/**
	 * 简单上传文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String upload(File file) throws IOException {
		try {
			Response res = uploadManager.put(file, file.getName(), getUpToken());
			if (res.isOK()) {
				return Config.get("qiniu.domain") + file.getName();
			}
		} catch (QiniuException e) {
			Response r = e.response;
			log.error(r.toString());
			try {
				log.error(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return "";
	}

}
