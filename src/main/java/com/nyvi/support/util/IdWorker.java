package com.nyvi.support.util;

import java.util.UUID;

/**
 * <p>
 *  id生成器
 * </p>
 * @author czk
 */
public class IdWorker {
	
	/**
	* 主机和进程的机器码
	*/
	private static final Sequence WORKER = new Sequence();

	/**
	 * 获取SequenceId
	 * @return SequenceId
	 */
	public static long getId() {
		return WORKER.nextId();
	}

	/**
	 * 获取SequenceId字符串形式
	 * @return SequenceId
	 */
	public static String getIdStr() {
		return String.valueOf(WORKER.nextId());
	}

	/**
	 * 获取UUID (去掉"-")
	 * @return uuid
	 */
	public static synchronized String get32UUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
