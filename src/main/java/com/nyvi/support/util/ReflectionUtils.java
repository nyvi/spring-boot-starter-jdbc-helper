package com.nyvi.support.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 反射工具类
 * </p>
 * @author czk
 */
public class ReflectionUtils {

	/**
	 * 构造函数禁止new
	 */
	private ReflectionUtils() {

	}

	/**
	* <p>
	* 获取该类的所有属性列表
	* </p>
	* @param clazz 反射类
	* @return 属性列表
	*/
	public static List<Field> getFieldList(Class<?> clazz) {
		if (Objects.isNull(clazz)) {
			return null;
		}
		List<Field> fieldList = new LinkedList<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			// 过滤静态属性
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			// 过滤 transient关键字修饰的属性
			if (Modifier.isTransient(field.getModifiers())) {
				continue;
			}
			fieldList.add(field);
		}
		/* 处理父类字段 */
		Class<?> superClass = clazz.getSuperclass();
		if (superClass.equals(Object.class)) {
			return fieldList;
		}
		/* 排除重载属性 */
		return excludeOverrideSuperField(fieldList, getFieldList(superClass));
	}

	/**
	 * 排序重置父类属性
	 * @param fieldList 子类属性
	 * @param superFieldList 父类属性
	 * @return 去重后属性
	 */
	public static List<Field> excludeOverrideSuperField(List<Field> fieldList, List<Field> superFieldList) {
		// 子类属性
		Map<String, Field> fieldMap = Maps.newHashMap(fieldList.size() + superFieldList.size());
		for (Field field : fieldList) {
			fieldMap.put(field.getName(), field);
		}
		for (Field superField : superFieldList) {
			if (!fieldMap.containsKey(superField.getName())) {
				// 加入重置父类属性
				fieldList.add(superField);
			}
		}
		return fieldList;
	}

}
