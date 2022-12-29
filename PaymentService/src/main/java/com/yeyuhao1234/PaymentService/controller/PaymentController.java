package com.yeyuhao1234.PaymentService.controller;

import com.yeyuhao1234.PaymentService.model.PaymentRequest;
import com.yeyuhao1234.PaymentService.model.PaymentResponse;
import com.yeyuhao1234.PaymentService.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@Log4j2
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody
                                              PaymentRequest paymentRequest){
        return new ResponseEntity<>(
                paymentService.doPayment(paymentRequest),
                HttpStatus.OK);

    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(@PathVariable("orderId")
                                                                   String orderId){
        return new ResponseEntity<>(
                paymentService.getPaymentByOrderId(orderId)
                ,HttpStatus.OK
        );


    }



}
