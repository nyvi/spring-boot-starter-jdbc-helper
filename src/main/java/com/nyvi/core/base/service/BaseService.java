package com.nyvi.core.base.service;

import java.util.List;

import com.nyvi.core.base.dto.TableData;
import com.nyvi.core.base.mode.BaseDO;
import com.nyvi.core.base.query.BaseQuery;

/**
 * 公共接口
 * @author czk
 */
public interface BaseService<T extends BaseDO> {
	
	/**
     * 保存
     * @param t 实体
     * @return 影响行数
     * @throws Exception 异常
     */
	int save(T t) throws Exception;

    /**
     * 更新非空字段,id不能为空
     * @param t 实体
     * @return 影响行数
     * @throws Exception 异常
     */
    int update(T t) throws Exception;

    /**
     * 更新或保存,id为空时保存,非空更新
     * @param t 实体
     * @return 影响行数
     * @throws Exception 异常
     */
    int saveOrUpdate(T t) throws Exception;

    /**
     * 批量保存
     * @param list 实体集合
     * @return 影响行数
     * @throws Exception 异常
     */
    int batchSave(List<T> list) throws Exception;

    /**
     * 删除
     * @param id 主键
     * @return 影响行数
     * @throws Exception 异常
     */
    int delete(Long id) throws Exception;

    /**
     * 批量删除
     * @param idList id集合
     * @return 影响行数
     * @throws Exception 异常
     */
    int batchDelete(List<Long> idList) throws Exception;

    /**
     * 查询总数
     * @param query 查询条件
     * @param <Q> 查询实体
     * @return 影响行数
     */
    <Q extends BaseQuery> int getCount(Q query);

    /**
     * 查询实体
     * @param id 主键
     * @return 查不到返回null, 否则返回实体
     */
    T getEntity(Long id);

    /**
     * 查询列表
     * @param query 查询条件
     * @param <Q> 查询实体
     * @return 返回列表
     */
    <Q extends BaseQuery> List<T> getList(Q query);
    
    /**
     * 查询表格列表
     * @param query 查询条件
     * @param <Q> 查询实体
     * @return 表格显示对象
     */
    <Q extends BaseQuery> TableData<T> getTableData(Q query);
}
