package com.ulasalle.gestorcitasconsultaclinicaspring.controllers.exceptions;

import com.ulasalle.gestorcitasconsultaclinicaspring.services.exceptions.BusinessException;
import com.ulasalle.gestorcitasconsultaclinicaspring.services.exceptions.ErrorCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice(
        basePackages = "com.ulasalle.gestorcitasconsultaclinicaspring.controllers"
)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .sorted((error1, error2) -> error1.getField().compareTo(error2.getField()))
                .map(FieldError::getDefaultMessage)
                .toList();
        Map<String, Object> response = new HashMap<>();
        String joinedMessage = String.join(", ", errors);
        response.put("message", transformJoinedMessage(joinedMessage));
        response.put("status", "400");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private String transformJoinedMessage(String message) {
        if (message == null || !message.contains(", ")) {
            return message;
        }

        // Dividir por ", " y procesar cada parte después de la primera
        String[] parts = message.split(", ");
        StringBuilder result = new StringBuilder(parts[0]);

        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            if (!part.isEmpty()) {
                // Convertir la primera letra a minúscula
                String transformedPart = part.substring(0, 1).toLowerCase() + part.substring(1);

                // Si es el último elemento, usar " y " en lugar de ", "
                if (i == parts.length - 1) {
                    result.append(" y ").append(transformedPart);
                } else {
                    result.append(", ").append(transformedPart);
                }
            }
        }

        return result.toString();
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(
            BusinessException exception
    ) {
        ErrorCodeEnum errorCode = exception.getErrorCode();
        Map<String, Object> response = new HashMap<>();
        response.put("message", errorCode.getMessage());
        response.put("status", "409");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception exception) {
        String message = exception.getMessage();
        if (message != null && message.contains("JSON parse error")) {
            message = "El cuerpo de la solicitud está mal formado";
        }
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", "500");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}