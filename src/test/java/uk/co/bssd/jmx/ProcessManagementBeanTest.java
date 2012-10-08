package uk.co.bssd.jmx;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

import java.lang.management.ManagementFactory;

import org.junit.Before;
import org.junit.Test;

public class ProcessManagementBeanTest {

	private ProcessManagementBean bean;

	@Before
	public void before() {
		this.bean = new ProcessManagementBean(
				ManagementFactory.getPlatformMBeanServer());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCpuUsageIsWithinValidRange() {
		double cpuUsage = this.bean.cpuUsage();
		assertThat(cpuUsage,
				is(allOf(greaterThanOrEqualTo(0.0), lessThanOrEqualTo(100.0))));
	}

	@Test
	public void testHeapMemoryMaxIsGreaterThanZero() {
		assertThat(this.bean.heapMemoryMax(), is(greaterThan(0L)));
	}

	@Test
	public void testHeapMemoryUsedIsGreaterThanZero() {
		assertThat(this.bean.heapMemoryUsed(), is(greaterThan(0L)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHeapMemoryPercentageUsedIsWithinValidRange() {
		assertThat(this.bean.heapMemoryPercentageUsed(),
				is(allOf(greaterThan(0.0), lessThanOrEqualTo(100.0))));
	}
}