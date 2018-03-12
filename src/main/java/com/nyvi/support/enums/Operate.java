package com.nyvi.support.enums;

/**
 * 基本操作枚举
 * @author czk
 */
public enum Operate {
	/**
	 * 等于
	 */
	EQ("="),
	/**
	 * 大于
	 */
	GT(">"),
	/**
	 * 大于等于
	 */
	GTE(">="),
	/**
	 * 小于
	 */
	LT("<"),
	/**
	 * 小于等于
	 */
	LTE("<="),
	/**
	 * 全匹配: like %s%
	 */
	LIKE("like"),
	/**
	 * in 查询 in(), 字段必须为 List
	 */
	IN("in");

	private String value;

	public String getValue() {
		return value;
	}

	private Operate(String value) {
		this.value = value;
	}
}
