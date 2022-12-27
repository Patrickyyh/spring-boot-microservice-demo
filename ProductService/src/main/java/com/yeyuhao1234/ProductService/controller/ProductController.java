package com.yeyuhao1234.ProductService.controller;

import com.yeyuhao1234.ProductService.model.ProductRequest;
import com.yeyuhao1234.ProductService.model.ProductResponse;
import com.yeyuhao1234.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId){
            ProductResponse productResponse = productService.getProductById(productId);
            return new ResponseEntity<>(productResponse , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
        long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId , HttpStatus.CREATED);
    }

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId,
                                               @RequestParam long quantity)
    {
        productService.reduceQuantity(productId , quantity);
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
