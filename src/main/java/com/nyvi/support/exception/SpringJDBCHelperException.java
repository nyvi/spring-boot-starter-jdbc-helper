package com.nyvi.support.exception;

/**
 * <p>
 * SpringJDBCHelper 异常
 * </p>
 * @author czk
 */
public class SpringJDBCHelperException extends RuntimeException {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 522160850746758745L;

	public SpringJDBCHelperException() {
	}

	public SpringJDBCHelperException(String message) {
		super(message);
	}

	public SpringJDBCHelperException(String message, Throwable cause) {
		super(message, cause);
	}
}
