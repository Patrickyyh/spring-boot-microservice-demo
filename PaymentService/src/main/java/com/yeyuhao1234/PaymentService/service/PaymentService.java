package com.yeyuhao1234.PaymentService.service;

import com.yeyuhao1234.PaymentService.model.PaymentRequest;
import com.yeyuhao1234.PaymentService.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);


    PaymentResponse getPaymentByOrderId(String orderId);
}
