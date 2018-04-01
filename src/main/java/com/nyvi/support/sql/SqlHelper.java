package com.nyvi.support.sql;

import java.util.Map;

import com.nyvi.support.entity.Pagination;

/**
 * sql辅助类
 * @author czk
 */
public interface SqlHelper {

	/**
	 * 获取insert sql
	 * @param clazz 表反射实体类
	 * @param paramMap 非空参数
	 * @return insert sql
	 */
	String getInsertSql(Class<?> clazz, Map<String, Object> paramMap);

	/**
	 * 获取delete sql
	 * @param clazz 表反射实体类
	 * @return delete sql
	 */
	String getDeleteByIdSql(Class<?> clazz);

	/**
	 * 获取update sql
	 * @param clazz 表反射实体类
	 * @param paramMap 非空参数
	 * @return update sql
	 */
	String getUpdateSql(Class<?> clazz, Map<String, Object> paramMap);

	/**
	 * 获取select count sql
	 * @param clazz 表反射实体类
	 * @param qClazz 查询反射实体类
	 * @param paramMap 非空参数
	 * @return count sql
	 */
	String getSelectCountSql(Class<?> clazz, Class<?> qClazz, Map<String, Object> paramMap);

	/**
	 * 获取select by id sql
	 * @param clazz 表反射实体类
	 * @return select by id sql
	 */
	String getSelectByIdSql(Class<?> clazz);

	/**
	 * 获取select page sql 
	 * @param clazz 表反射实体类
	 * @param qClazz 查询反射实体类
	 * @param paramMap 非空参数
	 * @param page 分页参数
	 * @return select page sql
	 */
	String getSelectPageSql(Class<?> clazz, Class<?> qClazz, Map<String, Object> paramMap, Pagination page);

	/**
	 * 获取表主键
	 * @param clazz 表反射实体类
	 * @return 主键
	 */
	String getPrimaryKey(Class<?> clazz);

	/**
	 * 主键实现策略
	 * @param clazz 表反射实体类
	 * @param paramMap 非空参数
	 */
	void initTableKey(Class<?> clazz, Map<String, Object> paramMap);
}
