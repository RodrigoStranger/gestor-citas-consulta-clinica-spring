package com.ulasalle.gestorcitasconsultaclinicaspring.controllers.exceptions;

import com.ulasalle.gestorcitasconsultaclinicaspring.controllers.ResponseWrapper;
import com.ulasalle.gestorcitasconsultaclinicaspring.services.exceptions.BusinessException;
import com.ulasalle.gestorcitasconsultaclinicaspring.services.exceptions.ErrorCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice(
        basePackages = "com.ulasalle.gestorcitasconsultaclinicaspring.controllers"
)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        List<ResponseWrapper.FieldErrorDetail> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ResponseWrapper.FieldErrorDetail(
                        fieldError.getField(),
                        "VALIDATION_ERROR",
                        fieldError.getDefaultMessage()
                )).toList();
        return ResponseWrapper
                .validationError(errors)
                .toResponseEntity();
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(
            BusinessException exception
    ) {
        ErrorCodeEnum errorCode = exception.getErrorCode();
        return ResponseWrapper.error(
                errorCode.getMessage(),
                HttpStatus.CONFLICT.value(),
                errorCode.getCode()
        ).toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception exception) {
        String message = exception.getMessage();
        if (message != null && message.contains("JSON parse error")) {
            message = "El cuerpo de la solicitud está mal formado";
        }
        return ResponseWrapper.error(message, HttpStatus.INTERNAL_SERVER_ERROR.value()).toResponseEntity();
    }
}