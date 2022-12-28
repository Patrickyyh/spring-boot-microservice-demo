package com.yeyuhao1234.OrderService.service;
import com.yeyuhao1234.OrderService.entity.Order;
import com.yeyuhao1234.OrderService.external.client.PaymentService;
import com.yeyuhao1234.OrderService.external.client.ProductService;
import com.yeyuhao1234.OrderService.external.request.PaymentRequest;
import com.yeyuhao1234.OrderService.model.OrderRequest;
import com.yeyuhao1234.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImp implements  OrderService{

    @Autowired
    private OrderRepository orderRepository;
    // Order Entity -> save the data with Status Order created

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;


    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing Order request: {}", orderRequest);

        // Call api by feign.
        // Product service - Block Products (Reduce quantity)
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Creating Order with created Status");
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("Created")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        log.info("Order Place successfully with order Id: {}", order.getId());


        // payment service -> payment -> success -> Complete, else
        log.info("Callig payment Service to complete the payment");
        PaymentRequest paymentRequest = PaymentRequest
                .builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .build();

        String orderStatus = null;
        try{
            // Call payment service api by feign.
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully. Changing the ORDER status to placed");
            orderStatus = "PAYMENT_PLACED";
        }catch (Exception e){
            log.error("Error Occured in payment. Chaing the Order status to failed");
            orderStatus = "PAYMENT_FAIL";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Order Places successfully with Order ID");
        return order.getId();

    }
}
