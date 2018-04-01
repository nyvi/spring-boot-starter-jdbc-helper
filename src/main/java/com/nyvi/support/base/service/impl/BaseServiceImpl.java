package com.nyvi.support.base.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.nyvi.support.base.dao.BaseDAO;
import com.nyvi.support.base.dto.TableResult;
import com.nyvi.support.base.service.BaseService;
import com.nyvi.support.entity.Pagination;

/**
 * 公共接口实现类
 * @author czk
 */
public class BaseServiceImpl<T extends Serializable> implements BaseService<T> {

	/**
	 * BaseDAO
	 */
	@Autowired
	private BaseDAO<T> baseDAO;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int save(T t) throws Exception {
		return baseDAO.insert(t);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(T t) throws Exception {
		return baseDAO.update(t);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int batchSave(List<T> list) throws Exception {
		return baseDAO.batchSave(list);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public <ID extends Serializable> int delete(ID id) throws Exception {
		return baseDAO.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public <ID extends Serializable> int batchDelete(List<ID> idList) throws Exception {
		return baseDAO.batchDelete(idList);
	}

	@Override
	public <Q extends T> int getCount(Q query) {
		return baseDAO.getCount(query);
	}

	@Override
	public <ID extends Serializable> T getEntity(ID id) {
		return baseDAO.getEntity(id);
	}

	@Override
	public <Q extends T> List<T> getList(Q query) {
		return this.getList(query, Pagination.startPage(1, Integer.MAX_VALUE));
	}

	@Override
	public <Q extends T> List<T> getList(Q query, Pagination page) {
		return baseDAO.getList(query, page);
	}

	@Override
	public <Q extends T> TableResult<T> getTableData(Q query, Pagination page) {
		int count = this.getCount(query);
		if (count > 0) {
			List<T> list = this.getList(query, page);
			return TableResult.bulid(count, list);
		}
		return TableResult.empty();
	}

}
