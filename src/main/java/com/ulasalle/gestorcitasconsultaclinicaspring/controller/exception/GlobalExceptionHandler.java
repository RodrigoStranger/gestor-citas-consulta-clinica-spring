package com.ulasalle.gestorcitasconsultaclinicaspring.controller.exception;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.ResponseWrapper;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Comparator;
import java.util.List;

@RestControllerAdvice(
        basePackages = "com.ulasalle.gestorcitasconsultaclinicaspring.controller"
)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        List<ResponseWrapper.FieldErrorDetail> errors = exception.getBindingResult().getFieldErrors().stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(error -> new ResponseWrapper.FieldErrorDetail(
                    error.getField(),
                    error.getCode(),
                    error.getDefaultMessage()
                ))
                .toList();

        ResponseWrapper<Void> response = ResponseWrapper.validationError(errors);
        return response.toResponseEntity();
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(
            BusinessException exception
    ) {
        ResponseWrapper<Void> response = ResponseWrapper.error(
            exception.getErrorCode().getMessage(),
            400,
            exception.getErrorCode().getCode()
        );
        return response.toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception exception) {
        String message = exception.getMessage();
        if (message != null && message.contains("JSON parse error")) {
            message = "El cuerpo de la solicitud está mal formado";
        }

        ResponseWrapper<Void> response = ResponseWrapper.error(message, 500);
        return response.toResponseEntity();
    }
}