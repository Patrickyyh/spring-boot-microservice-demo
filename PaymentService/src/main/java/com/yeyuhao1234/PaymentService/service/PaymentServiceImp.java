package com.yeyuhao1234.PaymentService.service;

import com.yeyuhao1234.PaymentService.entity.TransactionDetails;
import com.yeyuhao1234.PaymentService.model.PaymentMode;
import com.yeyuhao1234.PaymentService.model.PaymentRequest;
import com.yeyuhao1234.PaymentService.model.PaymentResponse;
import com.yeyuhao1234.PaymentService.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Log4j2
public class PaymentServiceImp implements  PaymentService {

    @Autowired
    private TransactionDetailsRepository repository;

    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording payment Detail");
        log.info("Order Id : {}", paymentRequest.getOrderId());
        TransactionDetails transactionDetails
                = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .orderId(paymentRequest.getOrderId())
                .paymentStatus("SUCCESS")
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();
        repository.save(transactionDetails);
        log.info("Payment created success");
        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentByOrderId(String orderId) {
        TransactionDetails transactionDetails
                = repository.findByOrderId(Long.valueOf(orderId));
        PaymentResponse paymentResponse = PaymentResponse
                .builder()
                .paymentId(transactionDetails.getId())
                .orderId(transactionDetails.getOrderId())
                .status(transactionDetails.getPaymentStatus())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .paymentDate(transactionDetails.getPaymentDate())
                .amount(transactionDetails.getAmount())
                .build();
        return paymentResponse;
    }
}



