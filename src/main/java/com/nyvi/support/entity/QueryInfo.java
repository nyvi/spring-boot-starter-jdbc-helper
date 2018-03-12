package com.nyvi.support.entity;

import com.nyvi.support.enums.Operate;

/**
 * <p>
 * 表字段查询反射信息
 * </p>
 * @author czk
 */
public class QueryInfo {

	/**
	* 字段名
	*/
	private String column;

	/**
	 * 属性名
	 */
	private String property;

	/**
	 * 操作
	 */
	private Operate operate;

	/**
	 * 前缀
	 */
	private String prefix;

	/**
	 * 后缀
	 */
	private String suffix;

	public QueryInfo() {
		super();
	}

	public QueryInfo(String column, String property, Operate operate, String prefix, String suffix) {
		super();
		this.column = column;
		this.property = property;
		this.operate = operate;
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Operate getOperate() {
		return operate;
	}

	public void setOperate(Operate operate) {
		this.operate = operate;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
