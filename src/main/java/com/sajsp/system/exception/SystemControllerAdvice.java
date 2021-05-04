package com.sajsp.system.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2(topic = "exception")
@ControllerAdvice
public class SystemControllerAdvice {

	@ExceptionHandler(CashCountException.class)
	public ResponseEntity<?> handleNameNotProvidedException(CashCountException ex) {
		log.info(String.format("Exception occurred: %s", ex.getMessage()));

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex.getMessage());

		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentNotValidException ex) {

		List<String> errors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Getter
	public static class ApiError {

		private HttpStatus status;
		private String message;
		private List<String> errors;

		public ApiError(HttpStatus status, String message, List<String> errors) {
			super();
			this.status = status;
			this.message = message;
			this.errors = errors;
		}

		public ApiError(HttpStatus status, String message, String error) {
			super();
			this.status = status;
			this.message = message;
			errors = Arrays.asList(error);
		}
	}
}
