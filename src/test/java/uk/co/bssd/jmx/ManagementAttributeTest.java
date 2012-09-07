package uk.co.bssd.jmx;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManagementAttributeTest {

	private static final String MBEAN_NAME = "uk.co.bssd:type=simpleMBean";

	private static final String ATTRIBUTE_NAME_UNKNOWN_VALUE = "UnknownValue";
	private static final String ATTRIBUTE_NAME_INT_VALUE = "IntValue";

	private ManagementBeanServer server;
	private ManagementBean managementBean;

	private SimpleReportingService underlyingBean;

	@Before
	public void before() {
		this.underlyingBean = new SimpleReportingService();

		this.server = new ManagementBeanServer();
		this.server.registerManagementBean(MBEAN_NAME, this.underlyingBean);

		this.managementBean = this.server.findManagementBean(MBEAN_NAME);
	}
	
	@After
	public void after() {
		this.server.unregisterManagementBean(MBEAN_NAME);
	}
	
	@Test
	public void testHasAttributeReportsCorrectlyForAttributeThatDoesNotExist() {
		assertThat(this.managementBean.hasAttribute(ATTRIBUTE_NAME_UNKNOWN_VALUE), is(false));
	}
	
	@Test
	public void testHasAttributeReportsCorrectlyForAttributeThatDoesExist() {
		assertThat(this.managementBean.hasAttribute(ATTRIBUTE_NAME_INT_VALUE), is(true));
	}

	@Test(expected = JmxException.class)
	public void testFindAttributeThatDoesNotExistFails() {
		this.managementBean.findAttribute(ATTRIBUTE_NAME_UNKNOWN_VALUE,
				Integer.class);
	}
}