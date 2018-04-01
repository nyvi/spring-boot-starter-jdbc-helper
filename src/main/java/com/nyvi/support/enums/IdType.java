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
	AUTO("数据库ID自增"),
	/**
	 * 用户自定义
	 */
	INPUT("用户输入ID"),
	/**
	 * id_woeker 需要设置实例化id
	 */
	ID_WORKER("全局唯一ID"),
	/**
	 * uuid
	 */
	UUID("全局唯一ID");

	/**
	 * 描述
	 */
	private final String desc;

	IdType(final String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}
}
