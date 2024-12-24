package com.loma.kkr.common.util;

/** 
 * This Message Constants interface to return messages based on business
 * @author Akash
 */
public interface MessageConstants {
	
	// Registration Flow Messages
	public String USER_REGISTRATION_SUCCESS_KEY = "register.success";
	public String USER_EMAIL_ALREADY_EXISTS_KEY = "register.failure.email.exists";
	
	// Login Flow Messages
	public String USER_EMAIL_NOT_EXISTS_KEY = "login.failure.account.notexists";
	public String USER_ID_NOT_EXISTS_KEY = "login.failure.account.id.notexists";
	public String USER_LOGIN_SUCCESS_KEY = "login.success";
	public String USER_IS_NOT_ACTIVE_KEY = "login.deactive";
	
	public String USER_IS_ALREADY_ACTIVE_KEY = "user.already.active";
	public String USER_IS_ALREADY_DEACTIVE_KEY = "user.already.deactive";
	public String USER_SUCCESS_KEY = "user.success";
	public String INCORRECT_PASSWORD_KEY = "change-password.incorrect.password";
	
	// Role Master Flow Messages
	public String ROLE_ID_NOT_EXISTS_KEY = "role.failure.roleid.notexists";
	public String ROLE_IS_NOT_ACTIVE_KEY = "role.deactive";
	public String REGISTERED_USER_ROLES_KEY = "USERS";
	
	public String FAILED_KEY = "failed";
	public String SUCCESS_KEY = "success";
	public String NOT_EXIST_KEY = "not.exists";
	
	public String JWT_SECRET_KEY = "jwt.secret.key";
	
	
}
