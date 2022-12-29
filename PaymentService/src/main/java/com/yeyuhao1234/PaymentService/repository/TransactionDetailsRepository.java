package com.yeyuhao1234.PaymentService.repository;

import com.yeyuhao1234.PaymentService.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails , Long> {
    TransactionDetails findByOrderId(long orderId);
}
