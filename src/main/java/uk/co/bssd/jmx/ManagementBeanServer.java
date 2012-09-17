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