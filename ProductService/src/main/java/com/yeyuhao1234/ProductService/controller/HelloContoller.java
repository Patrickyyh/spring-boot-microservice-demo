package com.yeyuhao1234.ProductService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloContoller {
    @GetMapping("/api/hello")
    public String helloWorld(){
        return "Hellow how are you today";
    }
}
