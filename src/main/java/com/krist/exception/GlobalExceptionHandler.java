package com.krist.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.krist.dto.common.ErrorResponseDto;
import com.krist.exception.custom.BadRequestException;
import com.krist.exception.custom.ConflictException;
import com.krist.exception.custom.ForbiddenException;
import com.krist.exception.custom.NotFoundException;
import com.krist.exception.custom.UnauthorizedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /*
     * 400: Bad Request.
     * 
     * The server could not understand the request due to incorrect syntax. The client should NOT
     * repeat the request without modifications.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(BadRequestException e) {
        return getErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    /*
     * 401: Unauthorized.
     * 
     * Indicates that the request requires user authentication information. The client MAY repeat
     * the request with a suitable Authorization header field.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorizedException(UnauthorizedException e) {
        return getErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    /*
     * 403: Forbidden.
     * 
     * Unauthorized request. The client does not have access rights to the content. Unlike 401, the
     * client's identity is known to the server.
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDto> handleForbiddenException(ForbiddenException e) {
        return getErrorResponse(e, HttpStatus.FORBIDDEN);
    }

    /*
     * 404: Not Found.
     * 
     * The server can not find the requested resource.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException e) {
        return getErrorResponse(e, HttpStatus.NOT_FOUND);
    }

    /*
     * 409: Conflict.
     * 
     * The request could not be completed due to a conflict with the current state of the resource.
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleConflictException(ConflictException e) {
        return getErrorResponse(e, HttpStatus.CONFLICT);
    }

    /*
     * Global exception.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        return getErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponseDto> getErrorResponse(Exception e, HttpStatus httpStatus) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.status(httpStatus)
                .body(new ErrorResponseDto(httpStatus.value(), e.getMessage()));
    }
}
