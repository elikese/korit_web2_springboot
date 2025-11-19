package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.AddProductReqDto;
import com.koreait.spring_boot_study.dto.ModifyProductReqDto;
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

    // 왜 RequestBody로 id까지 전달받지 않고,
    // 굳이 PathVariable로 id는 따로 받아오나요?
    // -> RESTful 설계: URL과 요청 Method만으로도 뭐하는지 예측할 수 있다.
    // localhost:8080/product/1 PUT -> product에 1번 자원을 수정하는 것
    /*
    {
        "name" : "무선마우스",
        "price" : 10000
    }
    {
        "name" : " ",
        "price" : 500
    }
    */

    @PutMapping("/{id}")
    public ResponseEntity<?> putProduct(
            @PathVariable int id,
            @Valid @RequestBody ModifyProductReqDto dto
    ) {
        productService.modifyProduct(id, dto);
        return ResponseEntity.ok("수정완료");
    }




}
