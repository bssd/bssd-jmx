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