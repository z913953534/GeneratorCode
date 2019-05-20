package org.zsc.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.zsc.entity.BaseModel;

public class BaseUtil {
	/**
	 * 
	 * @Description 利用反射机制，将map对象的值存入实体类中
	 * @param map
	 * @param entity
	 * @return
	 * @throws Exception
	 * @author Enzo
	 * @date 2016年10月6日
	 */
	public static BaseModel reflect(Map<String, String> map, Object entity,
			BaseModel baseModel) throws Exception {
		// 获取实体类的所有属性，返回Field数组
		Field[] fields = entity.getClass().getDeclaredFields();
		// 遍历所有属性
		for (int j = 0; j < fields.length; j++) {
			// 获取属性的名字
			Field field = fields[j];
			String fieldName = field.getName();
			// 获得相应属性的getXXX和setXXX方法名称
			String setName = "set" + fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			// 获取属性的类型
			String type = field.getGenericType().toString();
			if (field.getModifiers() == 9) {// public:9,private:2
				continue;
			}
			// 获取相应的方法
			Method setMethod = entity.getClass().getMethod(setName,
					new Class[] { field.getType() });

			String mapValue = map.get(fieldName);
			if (mapValue == null || "".equals(mapValue)
					|| "null".equals(mapValue)) {// 如果实体类的属性值在map中不存在，则返回继续
				continue;
			}
			// 关键。。。可访问私有变量
			// field.setAccessible(true);
			try {
				if (type.equals("class java.lang.String")) {
					setMethod.invoke(entity, new Object[] { mapValue });
				} else if (type.equals("char")) {
					char val = mapValue.charAt(0);
					setMethod.invoke(entity, new Object[] { val });
				} else if (type.equals("class java.lang.Integer")
						|| type.equals("int")) {
					setMethod
							.invoke(entity, new Object[] { (int) Double
									.parseDouble(mapValue) });
				} else if (type.equals("class java.lang.Double")
						|| type.equals("double")) {
					setMethod.invoke(entity,
							new Object[] { Double.parseDouble(mapValue) });
				} else if (type.equals("class java.util.Date")) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date date = sdf.parse(mapValue);
					setMethod.invoke(entity, new Object[] { date });
				} else if (type.equals("class java.lang.Float")
						|| type.equals("float")) {
					setMethod.invoke(entity,
							new Object[] { Float.parseFloat(mapValue) });
				} else if (type.equals("class java.lang.Long")
						|| type.equals("long")) {
					setMethod.invoke(entity,
							new Object[] { Long.parseLong(mapValue) });
				} else if (type.equals("class java.lang.Short")) {
					setMethod.invoke(entity,
							new Object[] { Short.parseShort(mapValue) });
				} else if (type.equals("class java.lang.Boolean")
						|| type.equals("boolean")) {
					setMethod.invoke(entity,
							new Object[] { Boolean.parseBoolean(mapValue) });
				} else if (type.equals("class java.lang.Byte")
						|| type.equals("byte")) {
					setMethod.invoke(entity,
							new Object[] { Byte.parseByte(mapValue) });
				}
				/* Object value = getMethod.invoke(entity, new Object[] {}); */
			} catch (Exception e) {
				baseModel.setResultFlag(1);
				baseModel.setMessage(fieldName + "类型转换出错！");
				e.printStackTrace();
				return baseModel;
			}
		}
		baseModel.setData(entity);
		return baseModel;
	}
}
