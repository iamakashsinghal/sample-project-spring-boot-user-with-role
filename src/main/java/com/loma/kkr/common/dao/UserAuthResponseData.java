package com.loma.kkr.common.dao;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Auth Response Data class return the response with Access Token and User Data 
 * @author Akash
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuthResponseData implements Serializable {

	private static final long serialVersionUID = 4987276013363534724L;
	
	/** 
	 * Access Token which is return to user end
	 */
	@JsonProperty("accessToken")
	private String accessToken;
	
	/** 
	 * User details data which is return to user end
	 */
	@JsonProperty("user")
	private UserReturn userReturn;

}
