package com.example.blog.common.exceptions;

import com.example.blog.common.constants.ApplicationConstant;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

public class BlogServerException extends RuntimeException {

    private static final long serialVersionUID = 1436995162658277359L;
    private final String errorId;
    private final String traceId;

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public BlogServerException(String errorId, HttpStatus status, String traceId) {
        this.errorId = errorId;
        this.traceId = traceId;
        this.status = status;
    }

    public static BlogServerException badRequest(String errorId) {
        return new BlogServerException(errorId, HttpStatus.BAD_REQUEST, MDC.get(
                ApplicationConstant.TRACE_ID));
    }

    public static BlogServerException notFound(String errorId) {
        return new BlogServerException(errorId, HttpStatus.NOT_FOUND, MDC.get(
                ApplicationConstant.TRACE_ID));
    }

    public static BlogServerException dataSaveException(String errorId) {
        return new BlogServerException(errorId, HttpStatus.INTERNAL_SERVER_ERROR,
                MDC.get(ApplicationConstant.TRACE_ID));
    }

    public static BlogServerException internalServerException(String errorId) {
        return new BlogServerException(errorId, HttpStatus.INTERNAL_SERVER_ERROR,
                MDC.get(ApplicationConstant.TRACE_ID));
    }

    public static BlogServerException methodNotAllowed(String errorId) {
        return new BlogServerException(errorId, HttpStatus.UNAUTHORIZED,
                MDC.get(ApplicationConstant.TRACE_ID));
    }

    public static BlogServerException notAuthorized(String errorId) {
        return new BlogServerException(
                errorId,
                HttpStatus.FORBIDDEN,
                MDC.get(ApplicationConstant.TRACE_ID)
        );
    }

}
