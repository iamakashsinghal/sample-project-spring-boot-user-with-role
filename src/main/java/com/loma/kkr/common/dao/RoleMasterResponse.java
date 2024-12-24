package com.loma.kkr.common.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.loma.kkr.common.entity.RoleMaster;

import lombok.Data;

/**
 * User SignIn Response Data class return the response User Auth Response Data
 * @author Akash
 */
@Data
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleMasterResponse extends BaseResponse {

	private static final long serialVersionUID = 7913858153583955673L;
	
	@JsonProperty("user")
	private RoleMaster userReturn;
	
	/**
	 * @param code
	 * @param message
	 */
	public RoleMasterResponse(Integer code, String message) {
		super(code, message, null);
	}

}
