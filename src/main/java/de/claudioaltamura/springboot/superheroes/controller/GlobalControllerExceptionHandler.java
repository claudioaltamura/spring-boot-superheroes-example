package de.claudioaltamura.springboot.superheroes.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import de.claudioaltamura.springboot.superheroes.SuperheroNotFoundException;


@RestControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(SuperheroNotFoundException.class)
	@ApiResponse(responseCode = "404", description = "Superhero not found",
			content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = Error.class)) })
	public ResponseEntity<Error> handleNotFound(RuntimeException ex) {
		Error error = new Error(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	@ApiResponse(responseCode = "500", description = "internal server error",
			content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = Error.class)) })
	public ResponseEntity<Error> handleThrowable(RuntimeException ex) {
		Error error = new Error(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}