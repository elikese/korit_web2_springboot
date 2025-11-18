package com.koreait.spring_boot_study.repository;

import com.koreait.spring_boot_study.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    // DB 대용
    private List<Product> products = new ArrayList<>(
            Arrays.asList(
                    new Product(1, "노트북", 1500000),
                    new Product(2, "마우스", 30000),
                    new Product(3, "키보드", 80000),
                    new Product(4, "모니터", 350000)
            )
    );

    // 컨트롤러 -> 서비스 -> 레포지토리
    // 1. 다건조회(전체조회)
    public List<Product> findAllProducts() {
        return products;
    }

    // 2. 단건조회(상품 하나만 조회)
    // id로 상품이름 단건 조회
    public String findProductNameById(int id) {
        // Optional: 컨테이너 클래스(null 일수도있고 아닐수도있음)
        Optional<Product> optionalProduct = products.stream()
                .filter(product -> product.getId() == id)
                .findFirst(); 
        // 매칭되는 첫번째 객체를 옵셔널에 감싸서 리턴 
        // or 매칭이 안되면 null을 옵셔널에 감싸서 리턴

        // 옵셔널을 펼치는것 -> Repository에서 할까? Service 할까?

        // 옵셔널로 감싼 데이터가 null이라면
        if(optionalProduct.isEmpty()) {
            // 정석) 예외를 던져야한다.
            return "해당 id의 상품 없음";
        }

        String targetName = optionalProduct.get().getName();

        return targetName;
    }

    // 상품 추가
    public int insertProduct(String name, int price) {
        // id 최댓값 추적
        int maxId = products.stream()
                .map(product -> product.getId())
                .max((id1, id2) -> id1 - id2)
                .get();

        // for문 사용
        int maxId2 = 0;
        for (Product product : products) { // product 하나씩 꺼내옴
            if(product.getId() > maxId2) { // 비교
                maxId2 = product.getId(); // 꺼내온값이 크면 그것으로 업데이트
            }
        }

        Product product = new Product(maxId + 1, name, price);
        products.add(product);
        return 1; // 한줄추가 -> 1 리턴, n줄 추가 -> n리턴
    }



}
