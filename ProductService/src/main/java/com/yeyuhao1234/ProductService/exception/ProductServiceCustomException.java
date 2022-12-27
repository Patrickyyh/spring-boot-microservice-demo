package com.yeyuhao1234.ProductService.exception;

import lombok.Data;

@Data
public class ProductServiceCustomException extends RuntimeException {

    private String errorCodes;
    public ProductServiceCustomException(String message,  String errorCodes) {
        super(message);
        this.errorCodes = errorCodes;
    }
}
