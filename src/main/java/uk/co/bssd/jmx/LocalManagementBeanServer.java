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

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

public class LocalManagementBeanServer extends ManagementBeanServer {

	private final MBeanServer beanServer;

	public LocalManagementBeanServer() {
		this(ManagementFactory.getPlatformMBeanServer());
	}

	public LocalManagementBeanServer(MBeanServer server) {
		super(server);
		this.beanServer = server;
	}

	public void registerManagementBean(String name, Object bean) {
		try {
			this.beanServer.registerMBean(bean, objectName(name));
		} catch (Exception e) {
			throw new JmxException("Unable to register management bean", e);
		}
	}

	public void unregisterManagementBean(String mbeanName) {
		try {
			this.beanServer.unregisterMBean(objectName(mbeanName));
		} catch (Exception e) {
			// do nothing
		}
	}
}