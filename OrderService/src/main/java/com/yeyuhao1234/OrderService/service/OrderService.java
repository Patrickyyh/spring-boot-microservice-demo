package com.yeyuhao1234.OrderService.service;

import com.yeyuhao1234.OrderService.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
