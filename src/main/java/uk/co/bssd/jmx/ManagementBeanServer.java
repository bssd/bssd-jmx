package uk.co.bssd.jmx;

import static uk.co.bssd.jmx.ObjectNameFactory.objectName;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class ManagementBeanServer {

	private final MBeanServer beanServer;
	
	public ManagementBeanServer() {
		this.beanServer = ManagementFactory.getPlatformMBeanServer();
	}
	
	public ManagementBean findManagementBean(String name) {
		ObjectName objectName = objectName(name);
		if (!this.beanServer.isRegistered(objectName)) {
			throw new JmxException("No bean registered with name [" + name + "]");
		}
		return new ManagementBean(this.beanServer, objectName);
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