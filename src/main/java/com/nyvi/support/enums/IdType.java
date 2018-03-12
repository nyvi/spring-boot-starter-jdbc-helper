package com.nyvi.support.enums;

/**
 * <p>
 * 生成ID类型枚举类
 * </p>
 * @author czk
 */
public enum IdType {

	/**
	 * 数据库自己增
	 */
	AUTO(0, "数据库ID自增"),
	/**
	 * 用户自定义
	 */
	INPUT(1, "用户输入ID"),
	/**
	 * id_woeker 需要设置实例化id
	 */
	ID_WORKER(2, "全局唯一ID"),
	/**
	 * uuid
	 */
	UUID(3, "全局唯一ID");
	/**
	 * 主键
	 */
	private final int key;

	/**
	 * 描述
	 */
	private final String desc;

	IdType(final int key, final String desc) {
		this.key = key;
		this.desc = desc;
	}

	public int getKey() {
		return this.key;
	}

	public String getDesc() {
		return this.desc;
	}
}
