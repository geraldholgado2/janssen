package com.sajsp.system.exception;

import lombok.Getter;

@Getter
public class CashCountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;

	public CashCountException(String message) {
		super(message);
		this.message = message;
	}
}
