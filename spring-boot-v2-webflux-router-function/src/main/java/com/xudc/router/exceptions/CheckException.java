package com.xudc.router.exceptions;

import lombok.Data;

/**
 * @author xudc
 */
@Data
public class CheckException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	/**
	 * 异常字段的名字
	 */
	private String fieldName;
	/**
	 * 异常字段的值
	 */
	private String fieldValue;

	public CheckException(String fieldName, String fieldValue) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
