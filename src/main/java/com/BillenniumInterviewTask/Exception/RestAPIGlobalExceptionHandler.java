/**
 * 
 */
package com.BillenniumInterviewTask.Exception;

import java.util.Date;

import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class RestAPIGlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourNotFoundException
	(ResourceNotFoundException exception, WebRequest request){
		
		RestAPIErrormessages errorDetails = new RestAPIErrormessages(new Date(), exception.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(errorDetails,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RestAPIException.class)
	public ResponseEntity<?> handleAPIException
	(RestAPIException exception, WebRequest request){
		
		RestAPIErrormessages errorDetails = new RestAPIErrormessages(new Date(), exception.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(errorDetails,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGLobalException
	(Exception exception, WebRequest request){
		
		RestAPIErrormessages errorDetails = new RestAPIErrormessages(new Date(), exception.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> customValidationErrorHandling(MethodArgumentNotValidException exception)
	{
		RestAPIErrormessages errorDetails = new RestAPIErrormessages(new Date(), "Validation Error", exception.getBindingResult().getFieldError().getDefaultMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
}
