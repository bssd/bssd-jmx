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

import static uk.co.bssd.jmx.ObjectNameFactory.objectName;

import java.io.IOException;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

public class ManagementBeanServer {

	private final MBeanServerConnection connection;

	public ManagementBeanServer(MBeanServerConnection connection) {
		this.connection = connection;
	}

	public ManagementBean findManagementBean(String name) {
		ObjectName objectName = objectName(name);
		if (!isRegistered(objectName)) {
			throw new JmxException("No bean registered with name [" + name
					+ "]");
		}
		return new ManagementBean(this.connection, objectName);
	}
	
	public ProcessManagementBean processManagementBean() {
		return new ProcessManagementBean(this.connection);
	}

	private boolean isRegistered(ObjectName objectName) {
		try {
			return this.connection.isRegistered(objectName);
		} catch (IOException e) {
			throw new JmxException("Unable to check if bean is registered", e);
		}
	}
}