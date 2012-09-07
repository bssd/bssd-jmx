package uk.co.bssd.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class ManagementAttribute<T> {

	private final MBeanServer beanServer;
	private final ObjectName objectName;
	private final String attributeName;
	
	public ManagementAttribute(MBeanServer beanServer, ObjectName objectName,
			String attributeName) {
		this.beanServer = beanServer;
		this.objectName = objectName;
		this.attributeName = attributeName;
	}
	
	@SuppressWarnings("unchecked")
	public T value() {
		try {
			return (T)this.beanServer.getAttribute(this.objectName, this.attributeName);
		} catch (Exception e) {
			throw new JmxException("Unable to retrieve value for attribute", e);
		}
	}
}