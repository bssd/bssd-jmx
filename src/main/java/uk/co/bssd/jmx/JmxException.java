package uk.co.bssd.jmx;

public class JmxException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public JmxException(String message) {
		super(message);
	}
	
	public JmxException(String message, Throwable cause) {
		super(message, cause);
	}
}