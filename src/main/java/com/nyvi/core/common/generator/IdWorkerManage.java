package com.nyvi.core.common.generator;

/**
 * id生成器工厂
 * @author czk
 */
public class IdWorkerManage {

	private static IdWorker idWorkerInstance = null;

	/**
	 * 构造函数禁止new
	 */
	private IdWorkerManage() {

	}

	/**
	 * 获取
	 * single 单例模式(懒汉式，线程安全)
	 * @return 唯一id
	 */
	private static synchronized IdWorker getIdWorkerInstance() {
		if (idWorkerInstance == null) {
			idWorkerInstance = new IdWorker(1, 1);
		}
		return idWorkerInstance;
	}

	/**
	 * 获取唯一id
	 * @return id
	 */
	public static long getId() {
		return getIdWorkerInstance().getId();
	}

}
