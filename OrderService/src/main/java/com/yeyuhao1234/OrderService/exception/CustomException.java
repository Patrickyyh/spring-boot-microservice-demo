package com.yeyuhao1234.OrderService.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException{
    private String errorCode;
    private int stauts;

    public CustomException(String message, String errorCode, int stauts) {
        super(message);
        this.errorCode = errorCode;
        this.stauts = stauts;
    }
}
