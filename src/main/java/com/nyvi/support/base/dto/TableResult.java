package com.nyvi.support.base.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格返回数据
 * @author czk
 */
public class TableResult<T> {
	/**
	 * 总记录数
	 */
	Integer total;

	/**
	 * 显示数据
	 */
	List<T> rows;

	private TableResult(Integer total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	/**
	 * 客户端分页返回数据
	 * @param rows 显示数据
	 * @param <T> 实体对象
	 * @return 表格数据
	 */
	public static <T> TableResult<T> bulid(List<T> rows) {
		return new TableResult<>(null, rows);
	}

	/**
	 * 服务端分页返回数据
	 * @param total 总记录数
	 * @param rows 显示数据
	 * @param <T> 实体对象
	 * @return 表格数据
	 */
	public static <T> TableResult<T> bulid(int total, List<T> rows) {
		return new TableResult<>(total, rows);
	}

	/**
	 * 暂无数据
	 * @param <T> 实体对象
	 * @return 表格空数据
	 */
	public static <T> TableResult<T> empty() {
		return new TableResult<>(0, new ArrayList<>(0));
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "TableResult [total=" + total + ", rows=" + rows + "]";
	}
}
