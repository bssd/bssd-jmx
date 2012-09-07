package uk.co.bssd.jmx;

public class SimpleReportingService implements SimpleReportingServiceMBean{

	private int intValue;
	
	public SimpleReportingService() {
		super();
	}
	
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}
	
	@Override
	public int getIntValue() {
		return this.intValue;
	}
}