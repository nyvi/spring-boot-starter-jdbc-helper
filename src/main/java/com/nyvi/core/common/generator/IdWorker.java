package com.nyvi.core.common.generator;

/**
 * IdWorker
 * @author czk
 */
class IdWorker {

	/**
	 * 数据中心ID(0~31)
	 */
	private long datacenterId;
	/**
	 * 工作机器ID(0~31)
	 */
	private long workerId;
	/**
	 * 毫秒内序列(0~4095)
	 */
	private long sequence = 0L;
	/**
	 * 开始时间截 (2000-01-01)
	 */
	private final long twepoch = 946656000000L;
	/**
	 * 机器标识位数
	 */
	private final long workerIdBits = 5L;
	/**
	 * 数据标识id所占的位数
	 */
	private final long datacenterIdBits = 5L;
	/**
	 * 支持的最大机器id,结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
	 */
	private final long maxWorkerId = ~(-1L << workerIdBits);

	/**
	 * 支持的最大数据标识id,结果是31
	 */
	private final long maxDatacenterId = ~(-1L << datacenterIdBits);
	/**
	 * 序列在id中占的位数
	 */
	private final long sequenceBits = 12L;
	/**
	 * 机器ID偏左移12位
	 */
	private final long workerIdShift = sequenceBits;
	/**
	 * 数据中心ID左移17位 (12 + 5)
	 */
	private final long datacenterIdShift = sequenceBits + workerIdBits;
	/**
	 * 时间毫秒左移22位 (5+5+12)
	 */
	private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
	/**
	 * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
	 */
	private final long sequenceMask = ~(-1L << sequenceBits);
	/**
	 * 上次生成ID的时间截
	 */
	private long lastTimestamp = -1L;

	/**
	 * 构造函数
	 * @param workerId 工作id (0~31)
	 * @param datacenterId 数据中心id (0~31)
	 */
	IdWorker(long workerId, long datacenterId) {
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format("workerId 不能大于 %d 或小于  0", maxWorkerId));
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException(String.format("datacenterId 不能大于  %d 或小于 0", maxDatacenterId));
		}
		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	/**
	 * 获得下一个ID (该方法是线程安全的)
	 * @return id
	 */
	private synchronized long nextId() {

		long timestamp = timeGen();
		// 如果当前时间小于上一次id生成的时间戳,说明系统时钟回退过这个时候应当抛出异常
		if (timestamp < lastTimestamp) {
			throw new RuntimeException(String.format(
					"clock moved backwards. refusing to generate id for %d milliseconds.", lastTimestamp - timestamp));
		}
		// 如果是同一时间生成的，则进行毫秒内序列
		if (timestamp == lastTimestamp) {
			// 当前毫秒内，则+1
			sequence = (1 + sequence) & sequenceMask;
			// 毫秒内序列溢出
			if (0 == sequence) {
				// 阻塞到下一个毫秒,获得新的时间戳
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			// 时间戳改变，毫秒内序列重置
			sequence = 0;
		}
		// 上次生成ID的时间截
		lastTimestamp = timestamp;
		// 移位并通过或运算拼到一起组成64位的ID
		return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
				| (workerId << workerIdShift) | sequence;

	}

	/**
	 * 阻塞到下一个毫秒，直到获得新的时间戳
	 * @param lastTimestamp 上次生成ID的时间截
	 * @return 当前时间戳
	 */
	private long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	/**
	 * 获取当前时间戳
	 * @return 当前时间戳
	 */
	private long timeGen() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取id
	 * @return id
	 */
	public long getId() {
		return nextId();
	}

}
