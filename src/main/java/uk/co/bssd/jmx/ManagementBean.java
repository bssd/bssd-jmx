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

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

public class ManagementBean {

	private final MBeanServerConnection beanServerConnection;
	private final ObjectName objectName;

	public ManagementBean(MBeanServerConnection connection, ObjectName objectName) {
		this.beanServerConnection = connection;
		this.objectName = objectName;
	}

	public <T> ManagementAttribute<T> findAttribute(String name,
			Class<T> attributeClazz) {
		if (!hasAttribute(name)) {
			throw new JmxException("Attribute [" + name + "] does not exist for object [" + this.objectName + "]");
		}
		return new ManagementAttribute<T>(this.beanServerConnection, this.objectName,
				name);
	}
	
	public boolean hasAttribute(String name) {
		for (MBeanAttributeInfo attribute : listAttributes()) {
			if (attribute.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	private MBeanAttributeInfo[] listAttributes() {
		return mbeanInfo().getAttributes();
	}

	private MBeanInfo mbeanInfo() {
		try {
			return this.beanServerConnection.getMBeanInfo(this.objectName);
		} catch (Exception e) {
			throw new JmxException("Unable to get MBeanInfo for ObjectName: " + this.objectName, e);
		}
	}
}