package cn.hm55.platform.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


@Component
// TODO 把数据库的字段转成JavaBean
public class DbToJava {
	
	@Autowired
	private Sql2o sql2o;
	
	public void toBean(){
		try(Connection conn = sql2o.open()){
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
