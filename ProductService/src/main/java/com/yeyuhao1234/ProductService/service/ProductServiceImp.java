package com.yeyuhao1234.ProductService.service;

import com.yeyuhao1234.ProductService.entity.Product;
import com.yeyuhao1234.ProductService.exception.ProductServiceCustomException;
import com.yeyuhao1234.ProductService.model.ProductRequest;
import com.yeyuhao1234.ProductService.model.ProductResponse;
import com.yeyuhao1234.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImp implements  ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
       log.info("Adding Product");
       Product product = Product.builder()
               .productName(productRequest.getName())
               .quantity(productRequest.getQuantity())
               .price(productRequest.getPrice())
               .build();
       productRepository.save(product);
       log.info("Product created");
       return product.getProductId();

    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get the product for productId: {}",productId);
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("Product with given id not found",
                                                                        "PRODUCT_NOT_FOUND") );

        ProductResponse productResponse = new ProductResponse();
        // Copy properties from product to productResponse
        // Make sure that all property match with each other.
        BeanUtils.copyProperties(product , productResponse);
        return productResponse;

    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce the quantity {} for Id: {}" , quantity , productId);
        Product product
                = productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException(
                        "Product with given id not found",
                        "PRODUCT_NOT_FOUND"));
        if(product.getQuantity() < quantity){
            throw new ProductServiceCustomException(
                    "Product does not have sufficient quantity",
                    "INSUFFICIENT_QUANTITY"
            );
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Quantity updated Successfully !");

    }
}
