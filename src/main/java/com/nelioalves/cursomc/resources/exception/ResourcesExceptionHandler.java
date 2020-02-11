package com.nelioalves.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nelioalves.cursomc.services.exception.DataIntegrityException;
import com.nelioalves.cursomc.services.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourcesExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException erro, HttpServletRequest request){
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), erro.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> objectNotFound(DataIntegrityException erro, HttpServletRequest request){
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), erro.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
