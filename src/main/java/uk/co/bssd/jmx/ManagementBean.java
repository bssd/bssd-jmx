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