package com.example.campaign.util.exception.v1;


// TODO: exception-mapper..
public class BusinessCodedException extends RuntimeException {

	// some code to tell what happened. Maybe with i18n/translations..
	private final String code;

	public BusinessCodedException(String code) {
		this(code, code, null);
	}

	public BusinessCodedException(String code, String message) {
		this(code, message, null);
	}

	public BusinessCodedException(String code, Throwable cause) {
		this(code, code, cause);
	}

	public BusinessCodedException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
}
