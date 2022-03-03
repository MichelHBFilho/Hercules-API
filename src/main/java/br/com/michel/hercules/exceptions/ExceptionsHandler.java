package br.com.michel.hercules.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.michel.hercules.api.controller.dto.ErrorDto;

@ControllerAdvice
public class ExceptionsHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDto> nonExistentEmployee(final HttpServletRequest request,
			final NotFoundException e) {
		ErrorDto error = new ErrorDto();
		error.setStatus(404);
		error.setMessage("This " + e.getWhat() + " does not exists on our database");
		error.setPath(request.getRequestURI());

		return ResponseEntity.status(error.getStatus()).body(error);
	}
	
	@ExceptionHandler(InvalidCPFException.class)
	public ResponseEntity<ErrorDto> invalidCPF(final HttpServletRequest request,
			final InvalidCPFException e) {
		ErrorDto error = new ErrorDto();
		error.setStatus(400);
		error.setMessage("CPF: " + e.getInvalidCpf() + " Is invalid");
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(error.getStatus()).body(error);
	}
	
}
