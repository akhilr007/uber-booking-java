package com.akhil.uber_backend.uber_ride.advices;

import com.akhil.uber_backend.uber_ride.exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException exception){
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(RuntimeConflictException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeConflictException(RuntimeConflictException exception){
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInputValidationErrors(MethodArgumentNotValidException exception){
        List<String> errors = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Input Validation Failed")
                .subErrors(errors)
                .build();

        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(RideRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleRideRequestException(RideRequestException exception){
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .message(exception.getLocalizedMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(InsufficientAmountException.class)
    public ResponseEntity<ApiResponse<?>> handleInsufficientAmountException(InsufficientAmountException exception){
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(exception.getLocalizedMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(DriverNotAvailableException.class)
    public ResponseEntity<ApiResponse<?>> handleRideRequestException(DriverNotAvailableException exception){
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                .message(exception.getLocalizedMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException exception){
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleBadCredentialsException(AccessDeniedException exception){
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handleBadCredentialsException(BadCredentialsException exception){
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleUsernameNotFoundException(UsernameNotFoundException exception){
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception){
        exception.printStackTrace();
        ApiError apiError = ApiError.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError){
        return ResponseEntity.status(apiError.getHttpStatus())
                .body(new ApiResponse<>(apiError));
    }
}