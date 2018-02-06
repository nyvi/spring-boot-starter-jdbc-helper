package com.nyvi.core.exception;

/**
 * 找不到表异常
 * @author czk
 */
public class TableNotFoundException extends RuntimeException {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -4335355727231566931L;

	public TableNotFoundException(String msg) {
		super(msg);
	}
}
