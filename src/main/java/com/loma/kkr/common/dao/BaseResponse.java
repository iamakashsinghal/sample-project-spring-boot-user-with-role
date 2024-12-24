package com.loma.kkr.common.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base Response class is maintain the response this proper code, message and error
 * @author Akash
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse implements Serializable {

	private static final long serialVersionUID = -2028995598455255688L;

	/**
	 *  logging purpose - Mostly used in case of error 
	 */
	private String refId;

	/** 
	 * Application Status Codes
	 */
	private Integer code;

	/** 
	 * Application Response message 
	 */
	private String message;

	/** 
	 * Error list 
	 */
	private List<Error> errors = new ArrayList<Error>();

	/**
	 * Parameterized Constructor in Base Response class
	 */
	public BaseResponse(Integer code, String message, List<Error> errors) {
		this.code = code;
		this.message = message;
		this.errors = errors;
	}

	/**
	 * @param code
	 * @param message
	 * @param description
	 */
	public BaseResponse(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

}
