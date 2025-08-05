package com.ulasalle.gestorcitasconsultaclinicaspring.controller.exception;

import com.ulasalle.gestorcitasconsultaclinicaspring.controller.ResponseWrapper;
import com.ulasalle.gestorcitasconsultaclinicaspring.service.exception.ErrorCodeEnum;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<?> handleError() {
        ErrorCodeEnum errorCode = ErrorCodeEnum.RUTA_NO_ENCONTRADA;
        ResponseWrapper<Void> response = ResponseWrapper.error(
            errorCode.getMessage(),
            404,
            errorCode.getCode()
        );
        return response.toResponseEntity();
    }
}