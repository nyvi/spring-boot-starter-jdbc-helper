package com.nyvi.core.base.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.nyvi.core.base.dao.BaseDAO;
import com.nyvi.core.base.dto.TableData;
import com.nyvi.core.base.mode.BaseDO;
import com.nyvi.core.base.query.BaseQuery;
import com.nyvi.core.base.service.BaseService;

/**
 * 公共接口实现类
 * @author czk
 */
public class BaseServiceImpl<T extends BaseDO> implements BaseService<T> {

	/**
	 * BaseDAO
	 */
	@Autowired
	private BaseDAO<T> baseDAO;

	/**
	 * 保存
	 * @param t 实体
	 * @return 影响行数
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int save(T t) throws Exception {
		return baseDAO.save(t);
	}

	/**
	 * 更新非空字段,id不能为空
	 * @param t 实体
	 * @return 影响行数
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(T t) throws Exception {
		return baseDAO.update(t);
	}

	/**
	 * 更新或保存,id为空时保存,非空更新
	 * @param t 实体
	 * @return 影响行数
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveOrUpdate(T t) throws Exception {
		if (Objects.isNull(t.getId())) {
			return baseDAO.save(t);
		}
		return baseDAO.update(t);
	}

	/**
	 * 批量保存
	 * @param list 实体集合
	 * @return 影响行数
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int batchSave(List<T> list) throws Exception {
		return baseDAO.batchSave(list);
	}

	/**
	 * 删除
	 * @param id 主键
	 * @return 影响行数
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(Long id) throws Exception {
		return baseDAO.delete(id);
	}

	/**
	 * 批量删除
	 * @param idList id集合
	 * @return 影响行数
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int batchDelete(List<Long> idList) throws Exception {
		return baseDAO.batchDelete(idList);
	}

	/**
	 * 查询总数
	 * @param query 查询条件
	 * @return 影响行数
	 */
	@Override
	public <Q extends BaseQuery> int getCount(Q query) {
		return baseDAO.getCount(query);
	}

	/**
	 * 查询实体
	 * @param id 主键
	 * @return 查不到返回null, 否则返回实体
	 */
	@Override
	public T getEntity(Long id) {
		return baseDAO.getEntity(id);
	}

	/**
	 * 查询列表
	 * @param query 查询条件
	 * @return 返回列表
	 */
	@Override
	public <Q extends BaseQuery> List<T> getList(Q query) {
		return baseDAO.getList(query);
	}

	@Override
	public <Q extends BaseQuery> TableData<T> getTableData(Q query) {
		int count = this.getCount(query);
		if (count > 0) {
			List<T> list = this.getList(query);
			return TableData.bulid(count, list);
		}
		return TableData.empty();
	}
}
