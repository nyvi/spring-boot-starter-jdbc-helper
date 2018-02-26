package com.nyvi.core.exception;

/**
 * Service 异常
 * @author czk
 */
public class ServiceException extends RuntimeException {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 522160850746758745L;

	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
