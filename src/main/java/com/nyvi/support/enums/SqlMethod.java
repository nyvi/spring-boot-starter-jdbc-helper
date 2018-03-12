package com.nyvi.support.enums;

/**
 * <p>
 * 支持 SQL 方法
 * </p>
 * @author czk
 */
public enum SqlMethod {

	/**
	 * 插入 
	 */
	INSERT("insert", "插入一条数据", "insert into %s (%s) values (%s)"),

	/**
	 * 根据id删除
	 */
	DELETE_BY_ID("deleteById", "根据ID 删除一条数据", "delete from %s where %s=:%s"),

	/**
	 * 根据id更新
	 */
	UPDATE_BY_ID("updateById", "根据ID 选择修改数据", "update %s set %s where %s=:%s"),

	/**
	 * 根据id查找
	 */
	SELECT_BY_ID("selectById", "根据ID 查询一条数据", "select %s from %s where %s=:%s"),

	/**
	 * 查询数量
	 */
	SELECT_COUNT("selectCount", "查询满足条件总记录数", "select count(*) from %s where 1=1 %s"),

	/**
	 * 分页
	 */
	SELECT_PAGE("selectPage", "查询满足条件所有数据,分页", "select %s from %s where 1=1 %s %s %s"),

	/**
	 * 排序
	 */
	ORDER_BY("order by", "排序", "order by %s"),

	/**
	 * 分页
	 */
	LIMIT("limit", "分页", "limit %s,%s");

	private final String method;

	private final String desc;

	private final String sql;

	SqlMethod(final String method, final String desc, final String sql) {
		this.method = method;
		this.desc = desc;
		this.sql = sql;
	}

	public String getMethod() {
		return this.method;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getSql() {
		return this.sql;
	}
}
