package com.loma.kkr.common.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User Register Response Data class return the response with Access Token and User Data 
 * @author Akash
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterResponse extends BaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4072770827372829108L;
	
	/**
	 * User Auth Response Data is return accessToken and User Details Data
	 */
	@JsonProperty("data")
	private UserAuthResponseData data;

	/**
	 * @param code
	 * @param message
	 * @param data
	 */
	@Builder
	public UserRegisterResponse(Integer code, String message, UserAuthResponseData data) {
		super(code, message, null);
		this.data = data;
	}
}
