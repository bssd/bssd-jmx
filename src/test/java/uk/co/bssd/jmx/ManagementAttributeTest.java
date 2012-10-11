/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

	private SimpleReportingService underlyingBean;

	private LocalManagementBeanServer server;
	private ManagementBean managementBean;
	private ManagementAttribute<Integer> managementAttribute;

	@Before
	public void before() {
		this.underlyingBean = new SimpleReportingService();

		this.server = new LocalManagementBeanServer();
		this.server.registerManagementBean(MBEAN_NAME, this.underlyingBean);

		this.managementBean = this.server.findManagementBean(MBEAN_NAME);
		this.managementAttribute = this.managementBean.findAttribute(ATTRIBUTE_NAME_INT_VALUE, Integer.class);
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
	
	@Test
	public void testGetValueFromAttributeReturnsDefaultValueIfNoneSet() {
		assertThat(this.managementAttribute.value(), is(0));
	}
	
	@Test
	public void testGetValueFromAttributeReturnsCorrectAnswer() {
		int value = 6463;
		this.underlyingBean.setIntValue(value);
		assertThat(this.managementAttribute.value(), is(value));
	}
	
	
}