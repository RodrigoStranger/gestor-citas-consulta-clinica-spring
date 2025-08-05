package com.ulasalle.gestorcitasconsultaclinicaspring.services.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCodeEnum errorCode;

    public BusinessException(
            ErrorCodeEnum errorCode
    ) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
