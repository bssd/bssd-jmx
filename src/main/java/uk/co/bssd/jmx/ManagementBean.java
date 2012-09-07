package uk.co.bssd.jmx;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class ManagementBean {

	private final MBeanServer beanServer;
	private final ObjectName objectName;

	public ManagementBean(MBeanServer beanServer, ObjectName objectName) {
		this.beanServer = beanServer;
		this.objectName = objectName;
	}

	public <T> ManagementAttribute<T> findAttribute(String name,
			Class<T> attributeClazz) {
		if (!hasAttribute(name)) {
			throw new JmxException("Attribute [" + name + "] does not exist for object [" + this.objectName + "]");
		}
		return new ManagementAttribute<T>(this.beanServer, this.objectName,
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
			return this.beanServer.getMBeanInfo(this.objectName);
		} catch (Exception e) {
			throw new JmxException("Unable to get MBeanInfo for ObjectName: " + this.objectName, e);
		}
	}
}