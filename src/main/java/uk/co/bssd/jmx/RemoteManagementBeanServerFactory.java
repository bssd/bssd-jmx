package uk.co.bssd.jmx;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public final class RemoteManagementBeanServerFactory {

	private RemoteManagementBeanServerFactory() {
		super();
	}

	public static ManagementBeanServer managementBeanServer(String hostname,
			int port) {
		String url = connectionUrl(hostname, port);
		MBeanServerConnection connection = mbeanServerConnection(url);
		return new ManagementBeanServer(connection);
	}

	private static MBeanServerConnection mbeanServerConnection(String url) {
		try {
			JMXServiceURL jmxServiceUrl = new JMXServiceURL(url);
			JMXConnector jmxConnector = JMXConnectorFactory
					.connect(jmxServiceUrl);
			return jmxConnector.getMBeanServerConnection();
		} catch (Exception e) {
			throw new JmxException("Unable to obtain connection to url '" + url
					+ "'", e);
		}
	}

	private static String connectionUrl(String hostname, int port) {
		return String.format("service:jmx:rmi:///jndi/rmi://%s:%s/jmxrmi",
				hostname, port);
	}
}