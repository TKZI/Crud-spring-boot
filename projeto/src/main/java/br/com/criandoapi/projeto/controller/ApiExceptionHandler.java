package br.com.criandoapi.projeto.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String USER_MESSAGE = "Ocorreu um erro interno inesperado no sistema"
			+ ". Tente novamente e se o problema persistir, entre em contato com o administrador do sistema";
	
	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		
		if(body == null) {
			body = Problem.builder().timestamp(OffsetDateTime.now()).title(statusCode.toString())
					.status(statusCode.value()).userMessage(USER_MESSAGE).build();
		}else if(body instanceof String) {
			body = Problem.builder().timestamp(OffsetDateTime.now()).title((String) body)
					.status(statusCode.value()).userMessage(USER_MESSAGE).build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, statusCode, request);
	}
	
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente";
		
		BindingResult bindingResult = ex.getBindingResult();
		
		List<Problem.Object> objects = bindingResult.getAllErrors().stream()
				.map(objectError -> {
					String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					String name = objectError.getObjectName();
					
					if(objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					return Problem.Object.builder().nome(name)
							.userMessage(message).build();}).collect(Collectors.toList());
							
		Problem problem = createProblemBuilder((HttpStatus)status, problemType, detail).userMessage(detail)
				.objects(objects).build();
		
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	
	
	
	
	
	
	
	
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType,
			String detail){
		return Problem.builder().status(status.value())
				.detail(detail)
				.timestamp(OffsetDateTime.now())
				.type(problemType.getUri())
				.title(problemType.getTitle());
	}
	
	
	
	
}
