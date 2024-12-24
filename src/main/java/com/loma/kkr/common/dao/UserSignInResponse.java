package com.loma.kkr.common.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User SignIn Response Data class return the response User Auth Response Data
 * @author Akash
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSignInResponse extends BaseResponse {

	private static final long serialVersionUID = 7913858153583955673L;
	
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
	public UserSignInResponse(Integer code, String message, UserAuthResponseData data) {
		super(code, message, null);
		this.data = data;
	}
	
	/**
	 * @param code
	 * @param message
	 */
	public UserSignInResponse(Integer code, String message) {
		super(code, message, null);
	}

}
