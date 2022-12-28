package com.yeyuhao1234.PaymentService.service;

import com.yeyuhao1234.PaymentService.model.PaymentRequest;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);
}
