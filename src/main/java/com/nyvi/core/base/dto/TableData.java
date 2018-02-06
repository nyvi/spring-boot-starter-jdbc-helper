package com.nyvi.core.base.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 表格显示数据 bootstrap Table 数据格式
 * @author czk
 */
public class TableData<T> {

	/**
	 * 总记录数
	 */
	Integer total;

	/**
	 * 显示数据
	 */
	List<T> rows;

	private TableData(Integer total, List<T> rows) {
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
	public static <T> TableData<T> bulid(List<T> rows) {
		return new TableData<>(null, rows);
	}

	/**
	 * 服务端分页返回数据
	 * @param total 总记录数
	 * @param rows 显示数据
	 * @param <T> 实体对象
	 * @return 表格数据
	 */
	public static <T> TableData<T> bulid(int total, List<T> rows) {
		return new TableData<>(total, rows);
	}

	/**
	 * 暂无数据
	 * @param <T> 实体对象
	 * @return 表格空数据
	 */
	public static <T> TableData<T> empty() {
		return new TableData<>(0, new ArrayList<>(0));
	}
}
