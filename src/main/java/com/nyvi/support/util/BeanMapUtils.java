package com.nyvi.support.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.cglib.beans.BeanMap;

/**
 * beanMap 互转工具类
 * @author czk
 */
public class BeanMapUtils {

	/**
	 * 构造函数禁止new
	 */
	private BeanMapUtils() {

	}

	/**
	 * bean转为Map,不包含值空字段
	 * @param bean 实体bean
	 * @param <T> 泛型
	 * @return map,忽略null字段
	 */
	public static final <T> Map<String, Object> beanToMap(T bean) {
		return beanToMap(bean, true);
	}

	/**
	 * bean转为Map
	 * @param bean 实体bean
	 * @param ignore 是否忽略值为空的字段
	 * @param <T> 泛型
	 * @return map对象
	 */
	public static final <T> Map<String, Object> beanToMap(T bean, boolean ignore) {
		if (Objects.isNull(bean)) {
			return new HashMap<>(0);
		}
		BeanMap beanMap = BeanMap.create(bean);
		Map<String, Object> map = Maps.newHashMap(beanMap.size());
		for (Object key : beanMap.keySet()) {
			Object value = beanMap.get(key);
			if (ignore && Objects.isNull(value)) {
				continue;
			}
			map.put(key.toString(), value);
		}
		return map;
	}

	/**
	 * Map转Bean
	 * @param map map对象
	 * @param bean 实体
	 * @param <T> 泛型
	 * @return bean对象
	 */
	public static <T> T mapToBean(Map<String, Object> map, T bean) {
		BeanMap beanMap = BeanMap.create(bean);
		beanMap.putAll(map);
		return bean;
	}
}
