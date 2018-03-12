package com.nyvi.support.base.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.util.CollectionUtils;

import com.nyvi.support.base.query.BaseQuery;
import com.nyvi.support.sql.MysqlSqlHelper;
import com.nyvi.support.sql.SqlHelper;
import com.nyvi.support.util.BeanMapUtils;

/**
 * BaseDAO, dao不做参数验证
 * @author czk
 */
public class BaseDAO<T extends Serializable> {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private SqlHelper sqlHelper = new MysqlSqlHelper();

	private Class<T> cls;

	@SuppressWarnings("unchecked")
	public BaseDAO() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		cls = (Class<T>) type.getActualTypeArguments()[0];
	}

	/**
	 * 添加
	 * @param t 实体
	 * @return 影响行数
	 */
	public int insert(T t) {
		String insertSql = sqlHelper.getInsertSql(cls);
		Map<String, Object> paramMap = BeanMapUtils.beanToMap(t);
		sqlHelper.initTableKey(cls, paramMap);
		return getNamedParameterJdbcTemplate().update(insertSql, paramMap);
	}

	/**
	 * 删除
	 * @param id 主键
	 * @param <ID> 主键
	 * @return 影响行数
	 */
	public <ID extends Serializable> int deleteById(ID id) {
		String deleteByIdSql = sqlHelper.getDeleteByIdSql(cls);
		Map<String, Object> paramMap = Collections.singletonMap(sqlHelper.getPrimaryKey(cls), id);
		return getNamedParameterJdbcTemplate().update(deleteByIdSql, paramMap);
	}

	/**
	 * 更新非空字段,id不能为空
	 * @param t 实体
	 * @return 影响行数
	 */
	public int update(T t) {
		Map<String, Object> paramMap = BeanMapUtils.beanToMap(t);
		String updateSql = sqlHelper.getUpdateSql(cls, paramMap);
		return getNamedParameterJdbcTemplate().update(updateSql, paramMap);
	}

	/**
	 * 查询总数
	 * @param query 查询条件
	 * @param <Q> 查询条件
	 * @return 影响行数
	 */
	public <Q extends BaseQuery> int getCount(Q query) {
		Map<String, Object> paramMap = BeanMapUtils.beanToMap(query);
		String selectCountSql = sqlHelper.getSelectCountSql(cls, query.getClass(), paramMap);
		return getNamedParameterJdbcTemplate().queryForObject(selectCountSql, paramMap, Integer.class);
	}

	/**
	 * 查询实体
	 * @param id 主键
	 * @param <ID> 主键
	 * @return 查不到返回null, 否则返回实体
	 */
	public <ID extends Serializable> T getEntity(ID id) {
		String selectByIdSql = sqlHelper.getSelectByIdSql(cls);
		RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(cls);
		Map<String, Object> paramMap = Collections.singletonMap(sqlHelper.getPrimaryKey(cls), id);
		List<T> list = getNamedParameterJdbcTemplate().query(selectByIdSql, paramMap, rowMapper);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 查询列表
	 * @param query 查询条件
	 * @param <Q> 查询条件
	 * @return 返回列表
	 */
	public <Q extends BaseQuery> List<T> getList(Q query) {
		Map<String, Object> paramMap = BeanMapUtils.beanToMap(query);
		String selectPageSql = sqlHelper.getSelectPageSql(cls, query.getClass(), paramMap);
		RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(cls);
		return getNamedParameterJdbcTemplate().query(selectPageSql, paramMap, rowMapper);
	}

	/**
	 * 批量保存
	 * @param list list实体对象
	 * @return 影响行数
	 */
	public int batchSave(List<T> list) {
		String insertSql = sqlHelper.getInsertSql(cls);
		List<T> paramMapList = new ArrayList<>(list.size());
		Map<String, Object> paramMap = null;
		for (T t : list) {
			paramMap = BeanMapUtils.beanToMap(t);
			sqlHelper.initTableKey(cls, paramMap);
			paramMapList.add(BeanMapUtils.mapToBean(paramMap, t));
		}
		SqlParameterSource[] valueParameter = SqlParameterSourceUtils.createBatch(paramMapList.toArray());
		return getNamedParameterJdbcTemplate().batchUpdate(insertSql, valueParameter).length;
	}

	/**
	 * 批量删除
	 * @param idList id集合
	 * @param <ID> 主键
	 * @return 影响行数
	 */
	public <ID extends Serializable> int batchDelete(List<ID> idList) {
		String primaryKey = sqlHelper.getPrimaryKey(cls);
		@SuppressWarnings("unchecked")
		Map<String, Object>[] valueParameter = new Map[idList.size()];
		for (int i = 0, size = idList.size(); i < size; i++) {
			valueParameter[i] = Collections.singletonMap(primaryKey, idList.get(i));
		}
		String deleteByIdSql = sqlHelper.getDeleteByIdSql(cls);
		return namedParameterJdbcTemplate.batchUpdate(deleteByIdSql, valueParameter).length;
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
}
