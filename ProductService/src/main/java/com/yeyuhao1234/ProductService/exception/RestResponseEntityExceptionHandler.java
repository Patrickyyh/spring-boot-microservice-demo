package com.yeyuhao1234.ProductService.exception;

import com.yeyuhao1234.ProductService.model.ErrorResponse;
import com.yeyuhao1234.ProductService.model.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductServiceCustomException.class)
    public ResponseEntity<ErrorResponse> handleProductServiceException(ProductServiceCustomException exception){
        return new ResponseEntity<>(
                new ErrorResponse()
                        .builder()
                        .errorMessage(exception.getMessage())
                        .errorCode(exception.getErrorCodes())
                        .build(), HttpStatus.NOT_FOUND
        );

    }
}
