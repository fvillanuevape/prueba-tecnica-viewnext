package com.viewnext.infraestructure.exception;

import com.viewnext.infraestructure.rest.ResponseEntityBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<ApiErrorDetail> details = new ArrayList<>();


        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" Media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();

        ApiErrorDetail errorDetail= new ApiErrorDetail(builder.toString(),requestUri);
        details.add(errorDetail);

        ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Invalid JSON" ,details);

        return ResponseEntityBuilder.build(err);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ApiErrorDetail> details = new ArrayList<ApiErrorDetail>();

        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();

        ApiErrorDetail errorDetail= new ApiErrorDetail(ex.getMessage(),requestUri);
        details.add(errorDetail);

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Malformed JSON request" ,details);

        return ResponseEntityBuilder.build(err);
    }

    // Method Not Allowed
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        List<ApiErrorDetail> details = new ArrayList<ApiErrorDetail>();

        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();

        ApiErrorDetail errorDetail= new ApiErrorDetail(ex.getMethod() + " Method is Not Supported",requestUri);
        details.add(errorDetail);

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed" ,details);

        return ResponseEntityBuilder.build(err);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ApiErrorDetail> details = new ArrayList<ApiErrorDetail>();
        details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    return new ApiErrorDetail( error.getObjectName() + " : " + error.getDefaultMessage(),((ServletWebRequest)request).getRequest().getRequestURI());
                })
                .collect(Collectors.toList());

        ApiError err = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Validation Errors" ,
                details);

        return ResponseEntityBuilder.build(err);
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<ApiErrorDetail> details = new ArrayList<ApiErrorDetail>();
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();

        ApiErrorDetail errorDetail= new ApiErrorDetail(ex.getParameterName() +" parameter is missing" ,requestUri);
        details.add(errorDetail);

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Missing Parameters" ,details);

        return ResponseEntityBuilder.build(err);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {

        List<ApiErrorDetail> details = new ArrayList<ApiErrorDetail>();
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();

        ApiErrorDetail errorDetail= new ApiErrorDetail(ex.getMessage(),requestUri);
        details.add(errorDetail);

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Mismatch Type" ,details);

        return ResponseEntityBuilder.build(err);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(Exception ex, WebRequest request) {

        List<ApiErrorDetail> details = new ArrayList<ApiErrorDetail>();
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();

        ApiErrorDetail errorDetail= new ApiErrorDetail(ex.getMessage(),requestUri);
        details.add(errorDetail);

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Constraint Violation" ,details);

        return ResponseEntityBuilder.build(err);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {

        List<ApiErrorDetail> details = new ArrayList<ApiErrorDetail>();

        ApiErrorDetail errorDetail= new ApiErrorDetail(ex.getMessage(),"");
        details.add(errorDetail);

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.NOT_FOUND, "Resource Not Found" ,details);

        return ResponseEntityBuilder.build(err);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ApiErrorDetail> details = new ArrayList<ApiErrorDetail>();
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();

        ApiErrorDetail errorDetail= new ApiErrorDetail(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()),requestUri);
        details.add(errorDetail);

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Method Not Found" ,details);

        return ResponseEntityBuilder.build(err);

    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {


        List<ApiErrorDetail> details = new ArrayList<ApiErrorDetail>();
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();

        ApiErrorDetail errorDetail= new ApiErrorDetail(ex.getLocalizedMessage(),requestUri);
        details.add(errorDetail);

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Error occurred" ,details);

        return ResponseEntityBuilder.build(err);

    }

    @Override
    protected  ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request){
        List<ApiErrorDetail> details = new ArrayList<ApiErrorDetail>();
        String requestUri = ((ServletWebRequest)request).getRequest().getRequestURI();

        ApiErrorDetail errorDetail= new ApiErrorDetail(ex.getMessage(),requestUri);
        details.add(errorDetail);

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred" ,details);

        return ResponseEntityBuilder.build(err);
    }


}