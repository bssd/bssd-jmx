package uk.co.bssd.jmx;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManagementBeanServerTest {

	private static final String MBEAN_NAME = "uk.co.bssd:type=simpleMBean";

	private ManagementBeanServer server;

	@Before
	public void before() {
		this.server = new ManagementBeanServer();
	}

	@After
	public void after() {
		this.server.unregisterManagementBean(MBEAN_NAME);
	}

	@Test(expected = JmxException.class)
	public void testFindManagementBeanThatHasNotBeenRegistered() {
		this.server.findManagementBean(MBEAN_NAME);
	}

	@Test
	public void testFindManagementBeanThatHasBeenRegistered() {
		this.server.registerManagementBean(MBEAN_NAME,
				new SimpleReportingService());
	}

	@Test
	public void testUnregisterBeanThatHasNotBeenRegisteredDoesNothing() {
		this.server.unregisterManagementBean(MBEAN_NAME);
	}

	@Test(expected = JmxException.class)
	public void testUnregisteringManagementBeanMeansItCanNoLongerBeFound() {
		this.server.unregisterManagementBean(MBEAN_NAME);
		this.server.findManagementBean(MBEAN_NAME);
	}
}