package com.nyvi.core.common.util;

/**
 * 字符串工具类
 * @author czk
 */
public class StrUtils {

	/**
	 * 构造函数禁止new
	 */
	private StrUtils() {

	}

	/**
	 * 为空时，默认字符串
	 * <pre>
	 * StringUtils.defaultIfBlank(null, "NULL")  = "NULL"
	 * StringUtils.defaultIfBlank("", "NULL")    = "NULL"
	 * StringUtils.defaultIfBlank(" ", "NULL")   = "NULL"
	 * StringUtils.defaultIfBlank("bat", "NULL") = "bat"
	 * StringUtils.defaultIfBlank("", null)      = null
	 * </pre>
	 * @param str 字符串
	 * @param defaultStr 默认字符串
	 * @param <T> 泛型
	 * @return 字符串
	 */
	public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr) {
		return isBlank(str) ? defaultStr : str;
	}

	/**
	 * 判断字符串是否为空
	 * <pre>
	 * StrUtils.isNotBlank(null)      = false
	 * StrUtils.isNotBlank("")        = false
	 * StrUtils.isNotBlank(" ")       = false
	 * StrUtils.isNotBlank("bob")     = true
	 * StrUtils.isNotBlank("  bob  ") = true
	 * </pre>
	 * @param cs 字符串
	 * @return 非空为true,否则false
	 */
	public static boolean isNotBlank(final CharSequence cs) {
		return !isBlank(cs);
	}

	/**
	 * 判断字符串是否为空
	 * <pre>
	 * StrUtils.isBlank(null)      = true
	 * StrUtils.isBlank("")        = true
	 * StrUtils.isBlank(" ")       = true
	 * StrUtils.isBlank("bob")     = false
	 * StrUtils.isBlank("  bob  ") = false
	 * </pre>
	 * @param cs 字符串
	 * @return 空为true,否则为false
	 */
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
