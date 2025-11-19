package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.AddProductReqDto;
import com.koreait.spring_boot_study.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // db에 추가 -> Post
    @PostMapping("/add")
    public ResponseEntity<?> postProduct(
           @Valid @RequestBody AddProductReqDto dto
    ) {
        productService.addProduct(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED) // 201
                .body("성공");
    }

    // localhost:8080/product/1 - DELETE
    // DELETE 요청은 body를 포함할 수 있지만, 잘 사용하지 않는다.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        productService.removeProduct(id);
        return ResponseEntity.ok("삭제 완료");
    }




}
