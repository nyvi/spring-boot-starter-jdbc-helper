package com.nyvi.core.exception;

/**
 * 找不到字段异常
 * @author czk
 */
public class ColumnNotFoundException extends RuntimeException {

	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 8678987494945695468L;

	public ColumnNotFoundException(String msg) {
		super(msg);
	}
}
