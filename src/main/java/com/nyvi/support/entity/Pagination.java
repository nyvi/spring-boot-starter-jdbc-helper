package com.nyvi.support.entity;

import com.nyvi.support.util.StrUtils;

/**
 * <p>
 * 分页
 * </p>
 * @author czk
 */
public class Pagination {

	/**
	 * 默认分页个数
	 */
	private static final int DEFAULF_PAGE_SIZE = 10;

	/**
	 * 页码
	 */
	private Integer pageNumber;

	/**
	 * 每页显示个数
	 */
	private Integer pageSize;

	/**
	 * 排序
	 */
	private String order;

	/**
	 * 使用默认分页数
	 * @param pageNumber 页码
	 * @return 分页实体
	 */
	public static Pagination startPage(Integer pageNumber) {
		if (pageNumber <= 0) {
			throw new IllegalArgumentException("pageNumber 必须大于0");
		}
		return new Pagination(pageNumber, DEFAULF_PAGE_SIZE);
	}

	/**
	 * 分页
	 * @param pageNumber 页码
	 * @param pageSize 每页显示个数
	 * @return 分页实体
	 */
	public static Pagination startPage(Integer pageNumber, Integer pageSize) {
		Pagination pagination = startPage(pageNumber);
		if (pageSize <= 0) {
			throw new IllegalArgumentException("pageSize 必须大于0");
		}
		pagination.setPageSize(pageSize);
		return pagination;
	}

	/**
	 * 分页,带排序
	 * @param pageNumber 页码
	 * @param pageSize 每页显示个数
	 * @param order 排序
	 * @return 分页实体
	 */
	public static Pagination startAndOrder(Integer pageNumber, Integer pageSize, String order) {
		Pagination pagination = startPage(pageNumber, pageSize);
		if (StrUtils.isBlank(order)) {
			throw new IllegalArgumentException("order 不能为空");
		}
		pagination.setOrder(order);
		return pagination;
	}
	
	public Pagination() {
		super();
	}

	private Pagination(Integer pageNumber, Integer pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

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
}
