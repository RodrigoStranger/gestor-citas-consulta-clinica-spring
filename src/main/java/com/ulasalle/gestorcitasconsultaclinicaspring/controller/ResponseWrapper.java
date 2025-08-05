package com.ulasalle.gestorcitasconsultaclinicaspring.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
public class ResponseWrapper<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private String message;
    private int status;
    private String errorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldErrorDetail> errors;

    public static <T>ResponseWrapper<T> success(T data, String message) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setData(data);
        response.setMessage(message);
        response.setStatus(200);
        return response;
    }

    public static <T>ResponseWrapper<T> error(String message, int status) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setMessage(message);
        response.setStatus(status);
        return response;
    }

    public static <T> ResponseWrapper<T> validationError(List<FieldErrorDetail> errors) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setMessage("Ocurrió un error de validación");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setErrors(errors);
        return response;
    }

    public ResponseEntity<?> toResponseEntity() {
        if (this.status == 200) {
            return ResponseEntity.ok(this);
        } else {
            return ResponseEntity.status(this.status).body(this);
        }
    }

    public static <T>ResponseWrapper<T> error(String message, int status, String errorCode) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setMessage(message);
        response.setStatus(status);
        response.setErrorCode(errorCode);
        return response;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class FieldErrorDetail {
        private String field;
        private String code;
        private String message;
    }
}
