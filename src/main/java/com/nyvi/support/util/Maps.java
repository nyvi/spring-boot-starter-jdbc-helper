package com.nyvi.support.util;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Map工具类
 * @author czk
 */
public class Maps {

	/**
	 * 构造函数禁止new
	 */
	private Maps() {

	}

	/**
	* 最大容量
	*/
	private static final int MAX_POWER_OF_TWO = 1 << 30;

	/**
	 * 容量最小判断
	 */
	private static final int MIN = 3;

	/**
	 * 初始化HashMap
	 * @param expectedSize 初始化个数
	 * @param <K> key类型
	 * @param <V> value 类型
	 * @return HashMap对象
	 */
	public static <K, V> HashMap<K, V> newHashMap(int expectedSize) {
		return new HashMap<K, V>(capacity(expectedSize));
	}

	/**
	 * 初始化LinkedHashMap
	 * @param expectedSize 初始化个数
	 * @param <K> key类型
	 * @param <V> value 类型
	 * @return LinkedHashMap对象
	 */
	public static <K, V> HashMap<K, V> newLinkedHashMap(int expectedSize) {
		return new LinkedHashMap<K, V>(capacity(expectedSize));
	}

	/**
	 * Map初始化容量
	 * @param expectedSize 预计数据个数
	 * @return 初始化个数
	 */
	public static final int capacity(int expectedSize) {
		if (expectedSize < 0) {
			throw new IllegalArgumentException("Map初始化大小不能小于0");
		}
		if (expectedSize < MIN) {
			return expectedSize + 1;
		}
		if (expectedSize < MAX_POWER_OF_TWO) {
			return (int) ((float) expectedSize / 0.75F + 1.0F);
		}
		return Integer.MAX_VALUE;
	}

}
