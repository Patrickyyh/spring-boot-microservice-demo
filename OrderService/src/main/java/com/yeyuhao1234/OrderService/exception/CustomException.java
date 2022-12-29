package com.yeyuhao1234.OrderService.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomException extends RuntimeException{
    private String errorCode;
    private int stauts;

    public CustomException(String message, String errorCode, int stauts) {
        super(message);
        this.errorCode = errorCode;
        this.stauts = stauts;
    }
    public CustomException(String message){
        super(message);
    }
}
