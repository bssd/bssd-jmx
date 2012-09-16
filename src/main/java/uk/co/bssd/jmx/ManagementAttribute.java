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