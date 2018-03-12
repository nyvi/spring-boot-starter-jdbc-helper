package com.nyvi.support.util;

/**
 * <p>
 * 字符串工具类
 * </p>
 * @author czk
 */
public class StrUtils {

	/**
	 * 下划线字符
	 */
	public static final char UNDERLINE = '_';

	/**
	 * 空字符
	 */
	public static final String EMPTY = "";

	/**
	 * 构造函数禁止new
	 */
	private StrUtils() {

	}

	/**
	 * <p>
	 * 串驼峰式 转下划线格式(小写)
	 * StrUtils.camelToUnderline("userName") =  "user_name"
	 * </p>
	 * @param param 需要转换的字符串
	 * @return 转换好的字符串
	 */
	public static String camelToUnderline(String param) {
		if (isBlank(param)) {
			return EMPTY;
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c) && i > 0) {
				sb.append(UNDERLINE);
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 为空时，默认字符串
	 * <pre>
	 * StrUtils.defaultIfBlank(null, "NULL")  = "NULL"
	 * StrUtils.defaultIfBlank("", "NULL")    = "NULL"
	 * StrUtils.defaultIfBlank(" ", "NULL")   = "NULL"
	 * StrUtils.defaultIfBlank("bat", "NULL") = "bat"
	 * StrUtils.defaultIfBlank("", null)      = null
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
