package com.comvous.demo.exceptions;

import com.comvous.demo.exceptions.domain.ResourceNotFoundException;
import com.comvous.demo.exceptions.domain.UniquenessException;
import io.jsonwebtoken.security.SignatureException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // handleHttpMediaTypeNotSupported : triggers when the JSON is invalid
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        details.add(builder.toString());

        ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Invalid JSON" ,details);

        return ResponseEntityBuilder.build(err);
    }

    // handleHttpMessageNotReadable : triggers when the JSON is malformed
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Malformed JSON request" ,details);

        return ResponseEntityBuilder.build(err);
    }

    // handleMethodArgumentNotValid : triggers when @Valid fails
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getObjectName()+ " : " +error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError err = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                "Validation Errors" ,
                details);

        return ResponseEntityBuilder.build(err);
    }

    // handleMissingServletRequestParameter : triggers when there are missing parameters
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getParameterName() + " parameter is missing");

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Missing Parameters" ,details);

        return ResponseEntityBuilder.build(err);
    }

    // handleMethodArgumentTypeMismatch : triggers when a parameter's type does not match
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Mismatch Type" ,details);

        return ResponseEntityBuilder.build(err);
    }

    // handleConstraintViolationException : triggers when @Validated fails
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleConstraintViolationException(Exception ex, WebRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Constraint Violation" ,details);

        return ResponseEntityBuilder.build(err);
    }

    // handleResourceNotFoundException : triggers when there is no resource with the specified ID in BDD
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.NOT_FOUND, "Resource Not Found" ,details);

        return ResponseEntityBuilder.build(err);
    }

    // handleResourceNotFoundException : triggers when there is no resource with the specified ID in BDD
    @ExceptionHandler(UniquenessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleResourceUniqueViolationException(UniquenessException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY, "Resource Violates Uniqueness", details);

        return ResponseEntityBuilder.build(err);
    }

    // handleResourceNotFoundException : triggers when there is no resource with the specified ID in BDD
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.FORBIDDEN, "Access Denied", details);

        return ResponseEntityBuilder.build(err);
    }

    // handleResourceNotFoundException : triggers when there is no resource with the specified ID in BDD
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.UNAUTHORIZED, "Bad Credentials", details);

        return ResponseEntityBuilder.build(err);
    }

    // handleResourceNotFoundException : triggers when Jwt signature is invalid
    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleJwtSignatureNotMatching(SignatureException ex) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.UNAUTHORIZED, "You a smith ? :p", details);

        return ResponseEntityBuilder.build(err);
    }

    // handleNoHandlerFoundException : triggers when the handler method is invalid
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> details = new ArrayList<>();
        details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Method Not Found" ,details);

        return ResponseEntityBuilder.build(err);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        logger.error("Error : {} ", ex.getMessage(), ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());

        ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST, "Error occurred" ,details);

        return ResponseEntityBuilder.build(err);
    }
}
