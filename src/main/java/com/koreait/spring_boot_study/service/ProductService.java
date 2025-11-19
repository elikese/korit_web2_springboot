package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.dto.AddProductReqDto;
import com.koreait.spring_boot_study.entity.Product;
import com.koreait.spring_boot_study.exception.ProductInsertException;
import com.koreait.spring_boot_study.exception.ProductNotFoundException;
import com.koreait.spring_boot_study.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 1. 다건조회(상품 이름들만)
    // 형변환 / 비즈니스로직(로깅, 외부 api 호출...)
    public List<String> getAllProductNames() {
        // 1. stream을 사용하는 방법
        List<String> productNames = productRepository.findAllProducts()
                .stream().map(product -> product.getName())
                .collect(Collectors.toList());

        // 2. for문 사용
        List<String> productNames2 = new ArrayList<>();
        List<Product> products = productRepository.findAllProducts();
        for (Product product : products) {
            productNames2.add(product.getName());
        }
        return productNames;
    }

    // 2. 단건조회(상품 이름) - id 기준으로
    public String getProductNameById(int id) {
        String targetName = productRepository.findProductNameById(id);
        return targetName;
    }

    // 3. 상품추가(등록)
    public void addProduct(AddProductReqDto dto) {
        int successCount = productRepository
                .insertProduct(dto.getName(), dto.getPrice());

        if(successCount <= 0) {
            throw new ProductInsertException("상품등록 중 문제가 생겼습니다.");
        }
    }

    // 4. 상품삭제
    public void removeProduct(int id) {
        int successCount = productRepository.deleteProductById(id);
        if(successCount <= 0) {
            throw new ProductNotFoundException("해당 상품은 존재하지 않습니다.");
        }
    }

}
