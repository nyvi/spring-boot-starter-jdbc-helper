package com.nyvi.core.base.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.util.CollectionUtils;

import com.nyvi.core.annotation.Column;
import com.nyvi.core.annotation.Query;
import com.nyvi.core.annotation.Table;
import com.nyvi.core.base.mode.BaseDO;
import com.nyvi.core.base.query.BaseQuery;
import com.nyvi.core.common.generator.IdWorkerManage;
import com.nyvi.core.common.util.ArrayUtils;
import com.nyvi.core.common.util.BeanMapUtils;
import com.nyvi.core.common.util.Maps;
import com.nyvi.core.common.util.StrUtils;
import com.nyvi.core.enums.Operate;
import com.nyvi.core.exception.ColumnNotFoundException;
import com.nyvi.core.exception.TableNotFoundException;

/**
 * BaseDAO, dao不做参数验证
 * @author czk
 */
public class BaseDAO<T extends BaseDO> {

	/**
	 * jdbc模板
	 */
	@Autowired
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private Class<T> cls;

	@SuppressWarnings("unchecked")
	public BaseDAO() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		cls = (Class<T>) type.getActualTypeArguments()[0];
	}

	/**
	 * 保存
	 * @param t 实体
	 * @return 影响行数
	 */
	public int save(T t) {
		if (Objects.isNull(t.getId())) {
			t.setId(IdWorkerManage.getId());
		}
		t.setGmtCreate(new Date());
		t.setGmtModified(t.getGmtCreate());
		String sql = this.getInsertSql();
		Map<String, Object> valueMap = BeanMapUtils.beanToMap(t);
		return namedParameterJdbcTemplate.update(sql, valueMap);
	}

	/**
	 * 删除
	 * @param id 主键
	 * @return 影响行数
	 */
	public int delete(Long id) {
		String sql = this.getDeleteSql();
		Map<String, Object> paramMap = Collections.singletonMap(this.getPrimaryKey(), id);
		return namedParameterJdbcTemplate.update(sql, paramMap);
	}

	/**
	 * 更新非空字段,id不能为空
	 * @param t 实体
	 * @return 影响行数
	 */
	public int update(T t) {
		t.setGmtCreate(null);
		t.setGmtModified(new Date());
		Map<String, Object> valueMap = Maps.newHashMap(16);
		String sql = this.getUpdateSql(t, valueMap);
		return namedParameterJdbcTemplate.update(sql, valueMap);
	}

	/**
	 * 查询总数
	 * @param query 查询条件
	 * @param <Q> 查询条件
	 * @return 影响行数
	 */
	public <Q extends BaseQuery> int getCount(Q query) {
		Map<String, Object> paramMap = Maps.newHashMap(16);
		String sql = this.getCountSql(query, paramMap);
		return namedParameterJdbcTemplate.queryForObject(sql, paramMap, Integer.class);
	}

	/**
	 * 查询实体
	 * @param id 主键
	 * @return 查不到返回null, 否则返回实体
	 */
	public T getEntity(long id) {
		String sql = this.getSelectSql();
		RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(cls);
		Map<String, Object> paramMap = Collections.singletonMap(this.getPrimaryKey(), id);
		List<T> list = namedParameterJdbcTemplate.query(sql, paramMap, rowMapper);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 查询列表
	 * @param query 查询条件
	 * @param <Q> 查询条件
	 * @return 返回列表
	 */
	public <Q extends BaseQuery> List<T> getList(Q query) {
		Map<String, Object> paramMap = Maps.newHashMap(16);
		String sql = this.getSelectSql(query, paramMap);
		RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(cls);
		return namedParameterJdbcTemplate.query(sql, paramMap, rowMapper);
	}

	/**
	 * 批量保存
	 * @param list list实体对象
	 * @return 影响行数
	 */
	public int batchSave(List<T> list) {
		Date date = new Date();
		for (T t : list) {
			if (Objects.isNull(t.getId())) {
				t.setId(IdWorkerManage.getId());
			}
			t.setGmtCreate(date);
			t.setGmtModified(date);
		}
		String sql = this.getInsertSql();
		SqlParameterSource[] valueParameter = SqlParameterSourceUtils.createBatch(list.toArray());
		return namedParameterJdbcTemplate.batchUpdate(sql, valueParameter).length;
	}

	/**
	 * 批量删除
	 * @param idList id集合
	 * @return 影响行数
	 */
	public int batchDelete(List<Long> idList) {
		String primaryKey = this.getPrimaryKey();
		@SuppressWarnings("unchecked")
		Map<String, Object>[] valueParameter = new Map[idList.size()];
		for (int i = 0, size = idList.size(); i < size; i++) {
			valueParameter[i] = Collections.singletonMap(primaryKey, idList.get(i));
		}
		String sql = this.getDeleteSql();
		return namedParameterJdbcTemplate.batchUpdate(sql, valueParameter).length;
	}

	/**
	 * 获取添加的sql语句
	 * @return insert sql
	 */
	private String getInsertSql() {
		StringBuffer sql = new StringBuffer(256);
		Map<String, String> fieldMap = this.getFieldMap(true);
		sql.append("insert into ").append(this.getTableName()).append("( ");
		for (String value : fieldMap.values()) {
			sql.append(value).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(") values (");
		for (String key : fieldMap.keySet()) {
			sql.append(":").append(key).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		return sql.toString();
	}

	/**
	 * 获取删除语句
	 * @return sql语句
	 */
	private String getDeleteSql() {
		StringBuffer sql = new StringBuffer(128);
		String primaryKey = this.getPrimaryKey();
		sql.append("delete from ").append(this.getTableName()).append(" where ").append(primaryKey).append("=:")
				.append(primaryKey);
		return sql.toString();
	}

	/**
	 * 获取更新语句
	 * @param t 更新实体
	 * @param valueMap 更新参数
	 * @return sql语句
	 */
	private String getUpdateSql(T t, Map<String, Object> valueMap) {
		Map<String, String> fieldMap = this.getFieldMap(false);
		StringBuffer sql = new StringBuffer(256);
		valueMap.putAll(BeanMapUtils.beanToMap(t));
		sql.append("update ").append(this.getTableName()).append(" set ");
		for (String key : valueMap.keySet()) {
			if (fieldMap.containsKey(key)) {
				sql.append(fieldMap.get(key)).append("=:").append(key).append(",");
			}
		}
		sql.deleteCharAt(sql.length() - 1);
		String primaryKey = this.getPrimaryKey();
		sql.append(" where ").append(primaryKey).append("=:").append(primaryKey);
		return sql.toString();
	}

	/**
	 * 查询总数sql
	 * @param query 查询条件
	 * @param paramMap 查询参数
	 * @return sql语句
	 */
	private <Q extends BaseQuery> String getCountSql(Q query, Map<String, Object> paramMap) {
		StringBuffer sql = new StringBuffer(256);
		sql.append("select count(*) from ").append(this.getTableName()).append(" where 1=1 ");
		this.getWhereSql(sql, query, paramMap);
		return sql.toString();
	}

	/**
	 * 根据id查询
	 * @return sql语句
	 */
	private String getSelectSql() {
		String primaryKey = this.getPrimaryKey();
		StringBuffer sql = this.getSelectField();
		sql.append(" where ").append(primaryKey).append("=:").append(primaryKey);
		return sql.toString();
	}

	/**
	 * 根据查询条件查询
	 * @param query 查询条件
	 * @param paramMap 查询参数
	 * @return sql语句
	 */
	private <Q extends BaseQuery> String getSelectSql(Q query, Map<String, Object> paramMap) {
		StringBuffer sql = this.getSelectField();
		sql.append(" where 1=1 ");
		this.getWhereSql(sql, query, paramMap);
		// 排序
		if (StrUtils.isNotBlank(query.getOrder())) {
			sql.append(" order by ").append(query.getOrder());
		}
		// 分页
		if (Objects.nonNull(query.getPageSize())) {
			if (Objects.nonNull(query.getPageNumber())) {
				int offset = (query.getPageNumber() - 1) * query.getPageSize();
				if (!(query.getPageSize() == Integer.MAX_VALUE && offset == 0)) {
					sql.append(" limit ").append(offset).append(",").append(query.getPageSize());
				}
			} else {
				sql.append(" limit ").append(query.getPageSize());
			}
		}
		return sql.toString();
	}

	/**
	 * 获取条件sql
	 * @param query 查询条件
	 * @param whereMap 查询参数
	 * @return sql语句
	 */
	private <Q extends BaseQuery> void getWhereSql(StringBuffer whereSql, Q query, Map<String, Object> whereMap) {
		Field[] declaredFields = query.getClass().getDeclaredFields();
		if (ArrayUtils.isNotEmpty(declaredFields)) {
			whereMap.putAll(BeanMapUtils.beanToMap(query));
			for (Field field : declaredFields) {
				String fieldName = field.getName();
				if (whereMap.containsKey(fieldName) && field.isAnnotationPresent(Query.class)) {
					Query q = field.getAnnotation(Query.class);
					String value = StrUtils.defaultIfBlank(q.name(), fieldName);
					if (Operate.IN.equals(q.operate())) {
						if ("List".equals(field.getType().getSimpleName())) {
							whereSql.append(" and ").append(value).append(" in (:").append(fieldName).append(")");
						}
						continue;
					}
					// 添加前缀
					if (StrUtils.isNotBlank(q.prefix())) {
						String s = whereMap.get(fieldName).toString();
						whereMap.put(fieldName, q.prefix() + s);
					}
					// 添加后缀
					if (StrUtils.isNotBlank(q.suffix())) {
						String s = whereMap.get(fieldName).toString();
						whereMap.put(fieldName, s + q.suffix());
					}
					String operate = q.operate().getValue();
					whereSql.append(" and ").append(value).append(" ").append(operate).append(":").append(fieldName);
				}
			}
		}
	}

	/**
	 * 获取查询字段
	 * @return sql语句
	 */
	private StringBuffer getSelectField() {
		Map<String, String> fieldMap = this.getFieldMap(true);
		StringBuffer sql = new StringBuffer(256);
		sql.append("select ");
		for (String value : fieldMap.values()) {
			sql.append(value).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(" from ").append(this.getTableName());
		return sql;
	}

	/**
	 * 获取表名
	 * @return 表名
	 */
	private String getTableName() {
		if (cls.isAnnotationPresent(Table.class)) {
			Table table = cls.getAnnotation(Table.class);
			return StrUtils.isBlank(table.name()) ? cls.getSimpleName() : table.name();
		}
		throw new TableNotFoundException("table not found");
	}

	/**
	 * 获取主键
	 * @return 主键默认id
	 */
	private String getPrimaryKey() {
		return "id";
	}

	/**
	 * 获取字段Map
	 * @param containsId 是否包含主键
	 * @return {Map<字段名,表字段名>}
	 */
	private Map<String, String> getFieldMap(boolean containsId) {
		Field[] declaredFields = ArrayUtils.addAll(BaseDO.class.getDeclaredFields(), cls.getDeclaredFields());
		Map<String, String> fieldMap = null;
		if (ArrayUtils.isNotEmpty(declaredFields)) {
			fieldMap = new LinkedHashMap<>(declaredFields.length);
			for (Field field : declaredFields) {
				if (field.isAnnotationPresent(Column.class)) {
					Column column = field.getAnnotation(Column.class);
					if (!containsId && column.isKey()) {
						continue;
					}
					fieldMap.put(field.getName(), StrUtils.isBlank(column.name()) ? field.getName() : column.name());
				}
			}
		}
		if (CollectionUtils.isEmpty(fieldMap)) {
			throw new ColumnNotFoundException("column not found");
		}
		return fieldMap;
	}
}
