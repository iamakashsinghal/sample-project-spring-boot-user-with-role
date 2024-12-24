package com.loma.kkr.common.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is response class is used to Login Response to return user
 * @author Akash
 */
@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRes {
	
	/** 
	 * Email id which is return to user end
	 */
    private String email;
    
    /** 
	 * Token which is return to user end
	 */
    private String token;

    public LoginRes(String email, String token) {
        this.email = email;
        this.token = token;
    }
}
