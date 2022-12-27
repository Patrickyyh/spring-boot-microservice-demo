package com.yeyuhao1234.OrderService.service;
import com.yeyuhao1234.OrderService.entity.Order;
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

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing Order request: {}", orderRequest);
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("Created")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        order = orderRepository.save(order);
        log.info("Order Place successfully with order Id: {}", order.getId());
        return order.getId();
        // Order Entity -> save the data with Status Order created
        // Product service - Block Products (Reduce quantity)
        // payment service -> payment -> success -> Complete, else
        // Cancelled

    }
}
