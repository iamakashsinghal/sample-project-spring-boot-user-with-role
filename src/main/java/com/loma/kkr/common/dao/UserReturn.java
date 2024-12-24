package com.loma.kkr.common.dao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.loma.kkr.common.entity.RoleMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is original Response to return data User
 * @author Akash
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserReturn {
	
	/** 
	 * User Name which is return to user end
	 */
	@JsonProperty("user_name")
	private String userName;
	
	/** 
	 * User Email which is return to user end
	 */
	@JsonProperty("user_email")
	private String userEmail;
	
	/** 
	 * Role Details which is return to user end
	 */
	@JsonProperty("role_Id")
	private List<RoleMaster> roleMaster;

}
