package com.tb.commpt.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Tanbo
 * 
 */
public class BaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseException() {
		super();
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	protected List<Object> exceptionParams = new ArrayList<Object>();

	/**
	 * 用来添加一个异常参数
	 * 
	 * @param param
	 *            ：String 异常参数
	 */
	public void addExceParam(String param) {
		exceptionParams.add(param);
	}

	/**
	 * 添加异常参数
	 * 
	 * @param param
	 */
	public void addExceParam(List<Object> param) {
		exceptionParams.addAll(param);
	}

	/**
	 * 返回异常参数
	 * 
	 * @return
	 */
	public List<Object> getExceptionParam() {
		return exceptionParams;
	}

	/**
	 * 以字符串数组的类型返回异常参数
	 * 
	 * @return
	 */
	public String[] getExceptionParamAsString() {
		String[] result = new String[exceptionParams.size()];
		for (int i = 0; i < exceptionParams.size(); i++) {
			result[i] = (String) exceptionParams.get(i);
		}
		return result;
	}

}
