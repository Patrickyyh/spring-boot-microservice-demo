package com.yeyuhao1234.ProductService.service;

import com.yeyuhao1234.ProductService.model.ProductRequest;
import com.yeyuhao1234.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
