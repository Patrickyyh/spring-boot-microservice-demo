package com.yeyuhao1234.OrderService.external.client;


import com.yeyuhao1234.OrderService.external.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/api/v1/payment")
    public interface PaymentService {
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody
                                          PaymentRequest paymentRequest);
}
