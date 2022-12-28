package com.yeyuhao1234.PaymentService.service;

import com.yeyuhao1234.PaymentService.entity.TransactionDetails;
import com.yeyuhao1234.PaymentService.model.PaymentRequest;
import com.yeyuhao1234.PaymentService.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImp implements  PaymentService{

    @Autowired
    private TransactionDetailsRepository repository;
    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
       log.info("Recording payment Detail");
        TransactionDetails transactionDetails
                = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();
        repository.save(transactionDetails);
        log.info("Payment created success");
        return transactionDetails.getId();

    }
}
