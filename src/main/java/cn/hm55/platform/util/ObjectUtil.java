package cn.hm55.platform.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对象操作工具
 * @author zhangpeng
 *
 */
public class ObjectUtil {

	public static Map<String, Object> objectToMap(Object obj) throws Exception {
		if (obj == null)
			return null;

		Map<String, Object> map = new HashMap<String, Object>();

		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (key.compareToIgnoreCase("class") == 0) {
				continue;
			}
			Method getter = property.getReadMethod();
			Object value = getter != null ? getter.invoke(obj) : null;
			map.put(key, value);
		}

		return map;
	} 
	
	/**
	 * 专为SQL2O的List<Map>转Json
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> toListMap(List<Map<String,Object>> list){
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		for(Map<String,Object> map : list){
			Map<String,Object> resMap = new HashMap<String, Object>();
			for(String key: map.keySet()){
				resMap.put(key, map.get(key));
			}
			res.add(resMap);
		}	
		return res;
	}
	
	
}
