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

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

public class ManagementAttribute<T> {

	private final MBeanServerConnection beanServerConnection;
	private final ObjectName objectName;
	private final String attributeName;
	
	public ManagementAttribute(MBeanServerConnection connection, ObjectName objectName,
			String attributeName) {
		this.beanServerConnection = connection;
		this.objectName = objectName;
		this.attributeName = attributeName;
	}
	
	@SuppressWarnings("unchecked")
	public T value() {
		try {
			return (T)this.beanServerConnection.getAttribute(this.objectName, this.attributeName);
		} catch (Exception e) {
			throw new JmxException("Unable to retrieve value for attribute", e);
		}
	}
}