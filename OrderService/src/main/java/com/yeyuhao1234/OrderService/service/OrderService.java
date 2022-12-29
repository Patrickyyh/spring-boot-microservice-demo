package com.yeyuhao1234.OrderService.service;

import com.yeyuhao1234.OrderService.model.OrderRequest;
import com.yeyuhao1234.OrderService.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderById(long orderId);
}
