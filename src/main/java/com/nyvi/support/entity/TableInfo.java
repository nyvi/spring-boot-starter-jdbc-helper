package com.nyvi.support.entity;

import java.util.List;

import com.nyvi.support.enums.IdType;

/**
 * <p>
 * 数据库表反射信息
 * </p>
 * @author czk
 */
public class TableInfo {

	/**
	 * 表名称
	 */
	private String tableName;

	/**
	* 表主键ID 类型
	*/
	private IdType idType;
    
	/**
     * 表主键ID 属性名
     */
    private String keyProperty;

    /**
     * 表主键ID 字段名
     */
    private String keyColumn;
    
	/**
	* 表字段信息列表
	*/
	private List<TableFieldInfo> fieldList;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public String getKeyProperty() {
		return keyProperty;
	}

	public void setKeyProperty(String keyProperty) {
		this.keyProperty = keyProperty;
	}

	public String getKeyColumn() {
		return keyColumn;
	}

	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}

	public List<TableFieldInfo> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<TableFieldInfo> fieldList) {
		this.fieldList = fieldList;
	}

}
