package com.example.campaign.filter.errorhandler.v1.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ErrorBean extends HashMap<String, Object> {

	public ErrorBean(Map<String, Object> from) {
		super(from);
	}

	public Throwable getException() {
//		Object exception = get("exceptionObject");
//		if (exception != null && exception instanceof Throwable) {
//			return (Throwable) exception;
//		}
		return null;
	}

	public int getCode() {
		Object status = get("status");
		if (status != null) {
			return (int) status;
		}
		return 200;
	}

	public void setCode(int code) {
		put("status", code);
	}

	public String getReason() {
		Object error = get("error");
		if (error != null) {
			return String.valueOf(error);
		}
		int status = getCode();
		try {
			return HttpStatus.valueOf(status).getReasonPhrase();
		} catch (Exception ex) {
			// Unable to obtain a reason
			return "Http Status " + status;
		}
	}

	public String getResponseCode() {
		int code = getCode();
		if (code < 300) {
			return "OK";
		} else if (code < 400) {
			return "GO";
		} else if (code < 500) {
			return "ERROR";
		} else if (code < 600) {
			return "ERROR";
		} else if (code < 999) {
			return "ERROR";
		} else {
			return "OK";
		}
	}

	public String getMessage() {
		// maybe better later on
		return getReason();
	}
}
