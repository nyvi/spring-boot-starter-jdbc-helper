package com.nyvi.support.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.CollectionUtils;

import com.nyvi.support.annotation.Column;
import com.nyvi.support.annotation.Id;
import com.nyvi.support.annotation.Query;
import com.nyvi.support.annotation.Table;
import com.nyvi.support.entity.QueryInfo;
import com.nyvi.support.entity.TableFieldInfo;
import com.nyvi.support.entity.TableInfo;
import com.nyvi.support.enums.Operate;
import com.nyvi.support.exception.SpringJDBCHelperException;

/**
 * 实体类反射表辅助类
 * @author czk
 */
public class TableInfoHelper {

	/**
	* 缓存反射类表信息
	*/
	private static final Map<String, TableInfo> TABLEINFOCACHE = new ConcurrentHashMap<>();

	/**
	 * 缓存反射类表查询信息
	 */
	private static final Map<String, List<QueryInfo>> QUERYINFOCACHE = new ConcurrentHashMap<>();

	/**
	* 获取实体映射表信息
	* @param clazz 反射实体类
	* @return TableInfo
	*/
	public static TableInfo getTableInfo(Class<?> clazz) {
		String key = ClassUtils.getUserClass(clazz).getName();
		if (TABLEINFOCACHE.containsKey(key)) {
			return TABLEINFOCACHE.get(key);
		}
		return initTableInfo(clazz);
	}

	/**
	 * 获取表信息【初始化】
	 * @param clazz 反射实体类
	 * @return TableInfo
	 */
	public synchronized static TableInfo initTableInfo(Class<?> clazz) {
		if (clazz.isAnnotationPresent(Table.class)) {
			TableInfo tableInfo = new TableInfo();
			Table table = clazz.getAnnotation(Table.class);
			String tableName = StrUtils.defaultIfBlank(table.value(), StrUtils.camelToUnderline(clazz.getSimpleName()));
			tableInfo.setTableName(tableName);
			tableInfo.setFieldList(getTableFieldList(clazz));
			initTableKey(clazz, tableInfo);
			// 设置缓存
			TABLEINFOCACHE.put(clazz.getName(), tableInfo);
			return tableInfo;
		}
		throw new SpringJDBCHelperException(String.format("%s :table not found ", clazz.getName()));
	}

	/**
	 * 初始化表主键
	 * @param clazz 反射实体类
	 * @param tableInfo TableInfo
	 */
	public static void initTableKey(Class<?> clazz, TableInfo tableInfo) {
		List<Field> fieldList = ReflectionUtils.getFieldList(clazz);
		if (!CollectionUtils.isEmpty(fieldList)) {
			for (Field field : fieldList) {
				if (field.isAnnotationPresent(Id.class)) {
					Id id = field.getAnnotation(Id.class);
					String key = StrUtils.defaultIfBlank(id.value(), StrUtils.camelToUnderline(field.getName()));
					tableInfo.setIdType(id.type());
					tableInfo.setKeyColumn(key);
					tableInfo.setKeyProperty(field.getName());
					break;
				}
			}
		}
		if (StrUtils.isBlank(tableInfo.getKeyColumn())) {
			throw new SpringJDBCHelperException(String.format("%s :table key not found ", clazz.getName()));
		}
	}

	/**
	* 获取实体表查询信息
	* @param clazz 反射实体类
	* @return QueryInfoList
	*/
	public static List<QueryInfo> getQueryInfoList(Class<?> clazz) {
		String key = ClassUtils.getUserClass(clazz).getName();
		if (QUERYINFOCACHE.containsKey(key)) {
			return QUERYINFOCACHE.get(key);
		}
		return initQueryInfoList(clazz);
	}

	/**
	 * 获取查询信息【初始化】
	 * @param clazz 反射实体类
	 * @return QueryInfoList
	 */
	private static List<QueryInfo> initQueryInfoList(Class<?> clazz) {
		List<Field> fieldList = ReflectionUtils.getFieldList(clazz);
		List<QueryInfo> queryInfoList = null;
		if (!CollectionUtils.isEmpty(fieldList)) {
			queryInfoList = new ArrayList<>(fieldList.size());
			for (Field field : fieldList) {
				// 个性化查询 Query 注解
				if (field.isAnnotationPresent(Query.class)) {
					Query query = field.getAnnotation(Query.class);
					String columnName = StrUtils.defaultIfBlank(query.value(),
							StrUtils.camelToUnderline(field.getName()));
					queryInfoList.add(new QueryInfo(columnName, field.getName(), query.operate(), query.prefix(),
							query.suffix()));
					continue;
				}
				// 默认查询 Column 注解
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					String columnName = StrUtils.defaultIfBlank(column.value(),
							StrUtils.camelToUnderline(field.getName()));
					queryInfoList.add(
							new QueryInfo(columnName, field.getName(), Operate.EQ, StrUtils.EMPTY, StrUtils.EMPTY));
					continue;
				}
			}
		}
		if (CollectionUtils.isEmpty(queryInfoList)) {
			throw new SpringJDBCHelperException(String.format("%s :query not found ", clazz.getName()));
		}
		return queryInfoList;
	}

	/**
	 * 获取表字段信息
	 * @param clazz 反射实体类
	 * @return TableFieldInfoList
	 */
	public static List<TableFieldInfo> getTableFieldList(Class<?> clazz) {
		List<Field> fieldList = ReflectionUtils.getFieldList(clazz);
		List<TableFieldInfo> tableFieldInfoList = null;
		if (!CollectionUtils.isEmpty(fieldList)) {
			tableFieldInfoList = new ArrayList<>(fieldList.size());
			for (Field field : fieldList) {
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					String columnName = StrUtils.defaultIfBlank(column.value(),
							StrUtils.camelToUnderline(field.getName()));
					tableFieldInfoList.add(new TableFieldInfo(columnName, field.getName(), column.update()));
				}
			}
		}
		if (CollectionUtils.isEmpty(tableFieldInfoList)) {
			throw new SpringJDBCHelperException(String.format("%s :column not found ", clazz.getName()));
		}
		return tableFieldInfoList;
	}
}
