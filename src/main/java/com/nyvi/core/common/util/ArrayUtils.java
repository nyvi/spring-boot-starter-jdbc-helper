package com.nyvi.core.common.util;

import java.lang.reflect.Array;

/**
 * 数组工具类
 * @author czk
 */
public class ArrayUtils {

	/**
	 * 构造函数禁止new
	 */
	private ArrayUtils() {

	}

	/**
	 * 合并数组
	 * @param array1 数组1
	 * @param array2 数组1
	 * @param <T> 泛型对象
	 * @return 合并后的新数组
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] addAll(final T[] array1, final T... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final Class<?> type1 = array1.getClass().getComponentType();
		final T[] joinedArray = (T[]) Array.newInstance(type1, array1.length + array2.length);
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		try {
			System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		} catch (final ArrayStoreException ase) {
			final Class<?> type2 = array2.getClass().getComponentType();
			if (!type1.isAssignableFrom(type2)) {
				throw new IllegalArgumentException(
						"Cannot store " + type2.getName() + " in an array of " + type1.getName(), ase);
			}
			throw ase; // No, so rethrow original
		}
		return joinedArray;
	}

	/**
	 * 数组拷贝
	 * @param array 数组
	 * @param <T> 泛型对象
	 * @return 数组拷贝对象
	 */
	public static <T> T[] clone(final T[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * 是否为非空
	 * @param array 数组
	 * @param <T> 泛型对象
	 * @return 非空为true,否则false
	 */
	public static <T> boolean isNotEmpty(final T[] array) {
		return !isEmpty(array);
	}

	/**
	 * 是否为空
	 * @param array 数组
	 * @return 空为true,否则false
	 */
	public static boolean isEmpty(final Object[] array) {
		return getLength(array) == 0;
	}

	/**
	 * 获取数组长度, 为空返回0
	 * @param array 数组
	 * @return 长度
	 */
	public static int getLength(final Object array) {
		if (array == null) {
			return 0;
		}
		return Array.getLength(array);
	}
}
