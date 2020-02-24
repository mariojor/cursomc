package com.nelioalves.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.nelioalves.cursomc.services.exception.AuthorizationException;
import com.nelioalves.cursomc.services.exception.DataIntegrityException;
import com.nelioalves.cursomc.services.exception.FileException;
import com.nelioalves.cursomc.services.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourcesExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException erro, HttpServletRequest request) {
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.NOT_FOUND.value(), 
				"Nao encontrado", 
				erro.getMessage(), 
				request.getRequestURI());
				
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> objectNotFound(DataIntegrityException erro, HttpServletRequest request) {
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), 
				"Integridade de dados", 
				erro.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException erro, HttpServletRequest request) {

		ValidationError err = new ValidationError(
				System.currentTimeMillis(), 
				HttpStatus.UNPROCESSABLE_ENTITY.value(), 
				"Erro de validacao", 
				erro.getMessage(), 
				request.getRequestURI());
		
		for (FieldError x : erro.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException erro, HttpServletRequest request) {
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.FORBIDDEN.value(), 
				"Acesso negado", 
				erro.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> file(FileException erro, HttpServletRequest request) {
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), 
				"Erro de arquivo", 
				erro.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonService(AmazonServiceException erro, HttpServletRequest request) {
		
		HttpStatus code = HttpStatus.valueOf(erro.getErrorCode());
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				code.value(), 
				"Erro Amazon Service", 
				erro.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(code).body(err);
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClient(AmazonClientException erro, HttpServletRequest request) {
		
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), 
				"Erro Amazon Client", 
				erro.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3(AmazonS3Exception erro, HttpServletRequest request) {
		
		StandardError err = new StandardError(
				System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), 
				"Erro Amazon S3", 
				erro.getMessage(), 
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
