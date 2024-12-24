package com.loma.kkr.common.util.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/** 
 * This Custom Status Codes enum to return value of messages like Success 200
 * @author Akash
 */
public enum AppStatusCodes {
	
	SUCCESS(200),
	CREATED(201),
	USER_EMAIL_ALREADY_EXISTS(501),
	USER_EMAIL_NOT_EXISTS(502),
	USER_ID_NOT_EXISTS(503),
	USER_IS_NOT_ACTIVE(504),
	USER_IS_ALREADY_ACTIVE(505),
	USER_IS_ALREADY_DEACTIVE(506),
	ROLE_ID_NOT_EXISTS(508),
	ROLE_IS_NOT_ACTIVE(509),
	INCORRECT_PASSWORD(510);
	
	private Integer statusCode;

	/**
	 * @param statusCode
	 * @return to statusCode
	 */
	AppStatusCodes(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Integer value() {
		return this.statusCode;
	}

	/**
	 * @return String Json
	 */
	@JsonValue
	public String toString() {
		return String.valueOf(statusCode);
	}

	/**
	 * @param text
	 * @return
	 */
	public static AppStatusCodes fromValue(String text) {
		for (AppStatusCodes b : AppStatusCodes.values()) {
			if (String.valueOf(b.statusCode).equals(text)) {
				return b;
			}
		}
		return null;
	}
}
