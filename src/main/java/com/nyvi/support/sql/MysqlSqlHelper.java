package com.nyvi.support.sql;

import java.util.List;
import java.util.Map;

import com.nyvi.support.entity.QueryInfo;
import com.nyvi.support.entity.TableFieldInfo;
import com.nyvi.support.entity.TableInfo;
import com.nyvi.support.enums.IdType;
import com.nyvi.support.enums.Operate;
import com.nyvi.support.enums.SqlMethod;
import com.nyvi.support.util.IdWorker;
import com.nyvi.support.util.StrUtils;
import com.nyvi.support.util.TableInfoHelper;

/**
 * <p>
 *  mysql 实现 sqlHelper
 * </p>
 * @author czk
 */
public class MysqlSqlHelper implements SqlHelper {

	/**
	 * 页码
	 */
	private final static String PAGE_NUMBER = "pageNumber";

	/**
	 * 每页显示个数
	 */
	private final static String PAGE_SIZE = "pageSize";

	/**
	 * 排序
	 */
	private final static String ORDER = "order";

	@Override
	public String getInsertSql(Class<?> clazz) {
		TableInfo table = getTable(clazz);
		StringBuilder fieldBuilder = new StringBuilder(128);
		StringBuilder placeholderBuilder = new StringBuilder(128);
		List<TableFieldInfo> fieldList = table.getFieldList();
		fieldBuilder.append(table.getKeyColumn());
		placeholderBuilder.append(":").append(table.getKeyProperty());
		for (TableFieldInfo tableFieldInfo : fieldList) {
			fieldBuilder.append(",").append(tableFieldInfo.getColumn());
			placeholderBuilder.append(",").append(":").append(tableFieldInfo.getProperty());
		}
		return String.format(SqlMethod.INSERT.getSql(), table.getTableName(), fieldBuilder.toString(),
				placeholderBuilder.toString());
	}

	@Override
	public String getDeleteByIdSql(Class<?> clazz) {
		TableInfo table = getTable(clazz);
		return String.format(SqlMethod.DELETE_BY_ID.getSql(), table.getTableName(), table.getKeyColumn(),
				table.getKeyProperty());
	}

	@Override
	public String getUpdateSql(Class<?> clazz, Map<String, Object> paramMap) {
		TableInfo table = getTable(clazz);
		StringBuilder fieldBuilder = new StringBuilder(128);
		List<TableFieldInfo> fieldList = table.getFieldList();
		for (TableFieldInfo tableFieldInfo : fieldList) {
			if (paramMap.containsKey(tableFieldInfo.getProperty())) {
				fieldBuilder.append(tableFieldInfo.getColumn()).append("=:").append(tableFieldInfo.getProperty())
						.append(",");
			}
		}
		if (fieldBuilder.length() > 0) {
			fieldBuilder.deleteCharAt(fieldBuilder.length() - 1);
		}
		return String.format(SqlMethod.UPDATE_BY_ID.getSql(), table.getTableName(), fieldBuilder.toString(),
				table.getKeyColumn(), table.getKeyProperty());
	}

	@Override
	public String getSelectCountSql(Class<?> clazz, Class<?> qClazz, Map<String, Object> paramMap) {
		TableInfo table = getTable(clazz);
		return String.format(SqlMethod.SELECT_COUNT.getSql(), table.getTableName(),
				getWhereSql(clazz, qClazz, paramMap));
	}

	@Override
	public String getSelectByIdSql(Class<?> clazz) {
		TableInfo table = getTable(clazz);
		return String.format(SqlMethod.SELECT_BY_ID.getSql(), getSelectColum(table), table.getTableName(),
				table.getKeyColumn(), table.getKeyProperty());
	}

	@Override
	public String getSelectPageSql(Class<?> clazz, Class<?> qClazz, Map<String, Object> paramMap) {
		TableInfo table = getTable(clazz);
		return String.format(SqlMethod.SELECT_PAGE.getSql(), getSelectColum(table), table.getTableName(),
				getWhereSql(clazz, qClazz, paramMap), getOrder(paramMap), getLimitSql(paramMap));
	}

	@Override
	public String getPrimaryKey(Class<?> clazz) {
		TableInfo table = getTable(clazz);
		return table.getKeyProperty();
	}

	@Override
	public void initTableKey(Class<?> clazz, Map<String, Object> paramMap) {
		TableInfo table = getTable(clazz);
		IdType idType = table.getIdType();
		if (IdType.ID_WORKER.equals(idType)) {
			paramMap.put(table.getKeyProperty(), IdWorker.getId());
			return;
		}
		if (IdType.UUID.equals(idType)) {
			paramMap.put(table.getKeyProperty(), IdWorker.get32UUID());
			return;
		}
		if (IdType.INPUT.equals(idType)) {
			paramMap.remove(table.getKeyProperty());
			return;
		}
	}

	/**
	 * 获取查询字段
	 * @param table 表信息
	 * @return 字段
	 */
	private String getSelectColum(TableInfo table) {
		StringBuilder fieldBuilder = new StringBuilder(128);
		List<TableFieldInfo> fieldList = table.getFieldList();
		fieldBuilder.append(table.getKeyColumn());
		for (TableFieldInfo tableFieldInfo : fieldList) {
			fieldBuilder.append(",").append(tableFieldInfo.getColumn());
		}
		return fieldBuilder.toString();
	}

	/**
	 * 获取分页sql语句
	 * @param paramMap 查询参数
	 * @return 分页语句
	 */
	private String getOrder(Map<String, Object> paramMap) {
		if (paramMap.containsKey(ORDER)) {
			String order = paramMap.get(ORDER).toString();
			return String.format(SqlMethod.ORDER_BY.getSql(), StrUtils.camelToUnderline(order));
		}
		return "";
	}

	/**
	 * 获取分页sql语句
	 * @param paramMap 查询参数
	 * @return 分页语句
	 */
	private String getLimitSql(Map<String, Object> paramMap) {
		if (paramMap.containsKey(PAGE_NUMBER) && paramMap.containsKey(PAGE_SIZE)) {
			int pageNumber = (int) paramMap.get("pageNumber");
			int pageSize = (int) paramMap.get("pageSize");
			int offset = (pageNumber - 1) * pageSize;
			return String.format(SqlMethod.LIMIT.getSql(), offset, pageSize);
		}
		return "";
	}

	/**
	 * 获取where语句
	 * @param clazz 表反射实体类
	 * @param qClazz 查询反射实体类
	 * @param paramMap 查询参数
	 * @return 条件语句
	 */
	private String getWhereSql(Class<?> clazz, Class<?> qClazz, Map<String, Object> paramMap) {
		StringBuilder whereBuilder = new StringBuilder(128);
		List<QueryInfo> queryInfoList = getQueryInfoList(qClazz);
		for (QueryInfo queryInfo : queryInfoList) {
			String fieldName = queryInfo.getProperty();
			if (paramMap.containsKey(fieldName)) {
				if (Operate.IN.equals(queryInfo.getOperate())) {
					whereBuilder.append(" and ").append(queryInfo.getColumn()).append(" in (:").append(fieldName)
							.append(")");
					continue;
				}
				// 添加前缀
				if (StrUtils.isNotBlank(queryInfo.getPrefix())) {
					String s = paramMap.get(fieldName).toString();
					paramMap.put(fieldName, queryInfo.getPrefix() + s);
				}
				// 添加后缀
				if (StrUtils.isNotBlank(queryInfo.getSuffix())) {
					String s = paramMap.get(fieldName).toString();
					paramMap.put(fieldName, s + queryInfo.getSuffix());
				}
				String operate = queryInfo.getOperate().getValue();
				whereBuilder.append(" and ").append(queryInfo.getColumn()).append(" ").append(operate).append(" :")
						.append(fieldName);
			}
		}
		return whereBuilder.toString();
	}

	/**
	 * 获取表信息
	 * @param clazz 表反射实体类
	 * @return 表信息
	 */
	private TableInfo getTable(Class<?> clazz) {
		return TableInfoHelper.getTableInfo(clazz);
	}

	/**
	 * 获取查询信息
	 * @param clazz 查询反射实体类
	 * @return 查询信息
	 */
	private List<QueryInfo> getQueryInfoList(Class<?> clazz) {
		return TableInfoHelper.getQueryInfoList(clazz);
	}

}
