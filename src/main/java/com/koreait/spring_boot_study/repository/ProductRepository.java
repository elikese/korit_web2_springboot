package com.koreait.spring_boot_study.repository;

import com.koreait.spring_boot_study.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
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

    // 단건 삭제
    // id를 통해서 단건을 삭제하는 메서드
    public int deleteProductById(int id) {
        // 매개변수로 들어온 id가 유효한지?
        // 유효하지 않으면 -> 0을 리턴
        Optional<Product> target = products.stream()
                .filter(product -> product.getId() == id)
                .findFirst(); // 매칭되는 첫번째 데이터를 옵셔널에 포장해서 들고오세요

        if(target.isEmpty()) { // 찾은 optional을 언패킹했더니 null이라면
            return 0;
        }
        // 코드가 진행이 된다는 것은 -> if문에 걸리지 않은것
        // 옵셔널을 언패킹
        Product product = target.get();
        // 제거
        products.remove(product);
        log.info("상품삭제완료: {}", product);
        return 1;
    }
    
    // 단건 업데이트
    public int updateProduct(int id, String name, int price) {
        // 매개변수로 들어온 id가 유효한 id인지
        Product target = null;
        for(Product product: products) {
            if(product.getId() == id) { // 매개변수로 들어온 id와 같다면
                target = product; // target을 업데이트
                break;
            }
        }
        if (target == null) { // target이 업데이트가 안되었다면
            // id는 유효하지 않는것!
            return 0; // 업데이트 0건 했어요
        }

        // List 업데이트
        // 리스트.set(index, 저장할데이터)
        // 리스트.indexOf(데이터) -> 해당 데이터의 index 번호를 리턴
        int index = products.indexOf(target);
        // entity 형태로 데이터베이스에 저장
        Product newProduct = new Product(id, name, price);
        // target이 있던자리에 newProduct가 저장됨
        products.set(index, newProduct);

        return 1;
    }



}
