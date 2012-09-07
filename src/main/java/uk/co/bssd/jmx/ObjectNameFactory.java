package uk.co.bssd.jmx;

import javax.management.ObjectName;

public final class ObjectNameFactory {

	private ObjectNameFactory() {
		super();
	}
	
	public static ObjectName objectName(String name) {
		try {
			return new ObjectName(name);
		} catch (Exception e) {
			throw new JmxException("Unable to construct ObjectName", e);
		}
	}
}