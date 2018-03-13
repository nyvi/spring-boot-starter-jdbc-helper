package com.nyvi.support.entity;

/**
 * <p>
 * 数据库表字段反射信息
 * </p>
 * @author czk
 */
public class TableFieldInfo {

	/**
	* 字段名
	*/
	private String column;

	/**
	 * 属性名
	 */
	private String property;

	/**
	 * 是否更新,默认true
	 */
	private boolean update;

	public TableFieldInfo() {
		super();
	}

	public TableFieldInfo(String column, String property, boolean update) {
		super();
		this.column = column;
		this.property = property;
		this.update = update;
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

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

}
