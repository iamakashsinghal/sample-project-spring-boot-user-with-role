package com.loma.kkr.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/** 
 * Custom Runtime Exception for business related errors and messages
 * @author Akash
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4295740555529242350L;

	private HttpStatus status;
	private Integer code;
	private String message;
	private String description;

	private String field;

	/**
	 * @param message
	 * @param cause
	 */
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 */
	public BusinessException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param status
	 * @param code
	 * @param message
	 */
	public BusinessException(HttpStatus status, Integer code, String message) {
		super();
		this.status = status;
		this.code = code;
		this.message = message;
	}

	/**
	 * @param status
	 * @param code
	 * @param message
	 * @param cause
	 */
	public BusinessException(HttpStatus status, Integer code, String message, Throwable cause) {
		super(cause);
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
