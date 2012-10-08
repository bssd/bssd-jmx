package uk.co.bssd.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import javax.management.MBeanServerConnection;

import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
public class ProcessManagementBean {

	private final OperatingSystemMXBean operatingSystemBean;
	private final MemoryMXBean memoryBean;
	private final int availableProcessors;

	private long lastSystemTime;
	private long lastProcessCpuTime;

	public ProcessManagementBean(MBeanServerConnection connection) {
		this.operatingSystemBean = operatingSystemBean(connection);
		this.memoryBean = memoryBean(connection);
		this.availableProcessors = this.operatingSystemBean
				.getAvailableProcessors();

		this.lastSystemTime = System.nanoTime();
		this.lastProcessCpuTime = this.operatingSystemBean.getProcessCpuTime();
	}
	
	public long heapMemoryMax() {
		return this.memoryBean.getHeapMemoryUsage().getMax();
	}
	
	public long heapMemoryUsed() {
		return this.memoryBean.getHeapMemoryUsage().getUsed();
	}
	
	public double heapMemoryPercentageUsed() {
		return (heapMemoryUsed() * 100) / heapMemoryMax();
	}

	public double cpuUsage() {
		long systemTime = System.nanoTime();
		long processCpuTime = this.operatingSystemBean.getProcessCpuTime();

		double cpuUsage = (100 * (processCpuTime - this.lastProcessCpuTime))
				/ (systemTime - this.lastSystemTime);

		lastSystemTime = systemTime;
		lastProcessCpuTime = processCpuTime;

		return Math.min(100.0, cpuUsage / this.availableProcessors);
	}
	
	private MemoryMXBean memoryBean(MBeanServerConnection connection) {
		return platformBean(connection, ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
	}

	private OperatingSystemMXBean operatingSystemBean(
			MBeanServerConnection connection) {
		return platformBean(connection,
				ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
				OperatingSystemMXBean.class);
	}

	private <T> T platformBean(MBeanServerConnection connection,
			String beanName, Class<T> clazz) {
		try {
			return ManagementFactory.newPlatformMXBeanProxy(connection,
					beanName, clazz);
		} catch (IOException e) {
			throw new RuntimeException("Unable to get OperatingSystemMXBean", e);
		}
	}
}
