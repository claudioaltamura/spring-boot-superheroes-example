package de.claudioaltamura.springboot.superheroes.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import de.claudioaltamura.springboot.superheroes.SuperheroNotFoundException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(SuperheroNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Error> handleNotFound(RuntimeException ex) {
		Error error = new Error(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Error> handleThrowable(RuntimeException ex) {
		Error error = new Error(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}