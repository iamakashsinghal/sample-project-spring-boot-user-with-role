package com.loma.kkr.common.dao;

import lombok.Getter;
import lombok.Setter;

/**
 * This is dto class is used to Login Request which is provide user
 * @author Akash
 */
@Getter
@Setter
public class LoginReq {
	
	/** 
	 * Email id which is provide user end
	 */
	private String email;
	
	/** 
	 * Password which is provide user end
	 */
	private String password;
}
