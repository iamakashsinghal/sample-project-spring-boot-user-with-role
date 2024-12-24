package com.loma.kkr.common.exception;

/** 
 * Custom No Data Found Exception for business related errors and messages
 * @author Akash
 */
public class NoDataFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoDataFoundException(String email) {
        super("No Record found " + email);
    }
}