package com.nyvi.support.base.query;

import java.io.Serializable;

/**
 * Base Query
 * @author czk
 */
@SuppressWarnings("serial")
public class BaseQuery implements Serializable {

	/**
	 * 页码
	 */
	private Integer pageNumber = 1;

	/**
	 * 每页显示个数
	 */
	private Integer pageSize = 10;

	/**
	 * 排序
	 */
	private String order;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "BaseQuery [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", order=" + order + "]";
	}
}
