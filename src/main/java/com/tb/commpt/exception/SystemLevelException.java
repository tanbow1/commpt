package com.tb.commpt.exception;

/**
 * 系统异常:程序出错时统一抛出该异常
 * 
 * @author Tanbo
 * 
 */
public class SystemLevelException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SystemLevelException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SystemLevelException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SystemLevelException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public SystemLevelException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
