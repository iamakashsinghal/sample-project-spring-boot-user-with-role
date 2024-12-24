package com.loma.kkr.common.exception;


/** 
 * Custom User Not Found Exception for business related errors and messages
 * @author Akash
 */
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String email) {
		super("User Id %d not found " + email);
	}
}