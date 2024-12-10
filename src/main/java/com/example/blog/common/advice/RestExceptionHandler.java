package com.example.blog.common.advice;

import com.example.blog.common.constants.ApplicationConstant;
import com.example.blog.common.constants.ErrorId;
import com.example.blog.common.exceptions.ApiError;
import com.example.blog.common.exceptions.BlogError;
import com.example.blog.common.exceptions.BlogServerException;
import com.example.blog.common.exceptions.ErrorCodeReader;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Set;

import static org.springframework.util.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.*;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError();

        BlogError blogError =
                new BlogError(ErrorId.SYSTEM_ERROR, ex.getLocalizedMessage());
        apiError.addError(blogError);
        ex.printStackTrace();

        return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object>
    handleConstraintViolationExceptionAllException(ConstraintViolationException ex, WebRequest request) {
        ApiError apiError = new ApiError();

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        violations.forEach(violation -> {
            BlogError blogError = getBlogError(violation.getMessageTemplate());
            apiError.addError(blogError);
        });
        return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BlogServerException.class)
    public final ResponseEntity<Object> handleBlogServerException(
            BlogServerException ex, WebRequest request) {
        ApiError apiError = new ApiError();

        System.out.println("EX" + ex.getErrorId());

        BlogError reservationError = getBlogError(ex.getErrorId());
        apiError.addError(reservationError);

        return new ResponseEntity(apiError, ex.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            BlogError reservationError = getBlogError(error.getDefaultMessage(),
                    buildErrorMessage(error));
            apiError.addError(reservationError);
        }
        return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError();

        if (e.getMostSpecificCause() instanceof BlogServerException) {
            BlogServerException reservationServerException = (BlogServerException) e.getMostSpecificCause();
            BlogError error = getBlogError(reservationServerException.getErrorId());
            apiError.addError(error);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        } else if (e.getMostSpecificCause() instanceof InvalidFormatException) {
            InvalidFormatException iex = (InvalidFormatException) e.getMostSpecificCause();
            iex.getPath().forEach(reference -> {
                BlogError engineeringManagementError = new BlogError(ErrorId.INVALID_DATA_FORMAT_EXCEPTION, iex.getOriginalMessage());
                apiError.addError(engineeringManagementError);
            });
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        return handleAllExceptions(e, request);
    }

    private BlogError getBlogError(String code) {
        BlogError blogError = ErrorCodeReader.getBlogError(code);

        if (Objects.isNull(blogError)) {
            return ErrorCodeReader.getErrorByMessage(code);
        }
        return blogError;
    }

    private BlogError getBlogError(String code, String message) {
        BlogError blogError = ErrorCodeReader.getBlogError(code);

        if (Objects.isNull(blogError)) {
            return ErrorCodeReader.getErrorByMessage(message);
        }
        return blogError;
    }

    private String buildErrorMessage(FieldError error) {
        return capitalize(StringUtils.join(splitByCharacterTypeCamelCase(emptyFieldErrorIfNull(error)
        ))) + SPACE + error.getDefaultMessage();
    }

    private String emptyFieldErrorIfNull(FieldError fieldError) {
        return Objects.isNull(fieldError) ? ApplicationConstant.EMPTY_STRING : fieldError.getField();
    }
}
