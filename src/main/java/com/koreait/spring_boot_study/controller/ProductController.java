package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 전체 상품명 조회
    @GetMapping("/name/all")
    public ResponseEntity<?> getProductNames() {
        return ResponseEntity.ok(productService.getAllProductNames());
    }

    // 상품명 단건 조회
    // localhost:8080/product/name/{id}
    @GetMapping("/name/{id}")
    public ResponseEntity<?> getProductName(@PathVariable int id) {
        return ResponseEntity.ok(productService.getProductNameById(id));
    }

}
