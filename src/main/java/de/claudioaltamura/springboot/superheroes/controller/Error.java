package de.claudioaltamura.springboot.superheroes.controller;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Error {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	private int errorCode;

	private String message;

	public Error(){}

	public Error(LocalDateTime timestamp, int errorCode, String message) {
		this.timestamp = timestamp;
		this.errorCode = errorCode;
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}
}
