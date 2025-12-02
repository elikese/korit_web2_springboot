package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.dto.req.AddProductReqDto;
import com.koreait.spring_boot_study.dto.req.ModifyProductReqDto;
import com.koreait.spring_boot_study.dto.req.SearchProductReqDto;
import com.koreait.spring_boot_study.dto.res.ProductQuantityResDto;
import com.koreait.spring_boot_study.dto.res.SearchProductResDto;
import com.koreait.spring_boot_study.dto.res.Top3SellingProductResDto;
import com.koreait.spring_boot_study.entity.Product;
import com.koreait.spring_boot_study.exception.ProductInsertException;
import com.koreait.spring_boot_study.exception.ProductNotFoundException;
import com.koreait.spring_boot_study.model.Top3SellingProduct;
import com.koreait.spring_boot_study.repository.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    // 인터페이스타입으로 필드를 가지고 있음
    // private ProductRepo productRepository;
    private ProductMapper productRepository;

    @Autowired
    public ProductService(ProductMapper productRepository) {
        /*
        ProductRepo -> 인터페이스
        인터페이스 타입의 객체는 존재하지 x -> 구현체가 있나 Ioc컨테이너를 검사
        ProductJdbcRepo, ProductRepository 둘다 ProductRepo를 implements 받았음
        여러개인 경우가 되어버림 -> 우선순위를 지정해줘서 해결 할 수 있다.
        1. 필드 변수명과 bean 이름이 같으면 매칭
        2. @Qualifier 사용
        3. @Primary를 사용
        */
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

    // 5. 상품업데이트
    public void modifyProduct(int id, ModifyProductReqDto dto) {
        int successCount = productRepository
                .updateProduct(id, dto.getName(), dto.getPrice());
        if(successCount <= 0) {
            throw new ProductNotFoundException("해당 상품은 존재하지 않습니다.");
        }
    }

    // Top3 상품들 리턴해주는 메서드(model을 리턴하면 안됨)
    public List<Top3SellingProductResDto> getTop3SellingProduct() {
        List<Top3SellingProduct> results = productRepository.findTop3SellingProducts();
        List<Top3SellingProductResDto> outputs = new ArrayList<>();
        for(Top3SellingProduct result : results) {
            Top3SellingProductResDto dto
                    = Top3SellingProductResDto.from(result);

            outputs.add(dto);
        }
        return outputs;
//        return productRepository.findTop3SellingProducts().stream()
//                // 메서드참조로 축약할 수 있다.
//                .map(model -> Top3SellingProductResDto.from(model))
//                .collect(Collectors.toList());
    }

    public List<ProductQuantityResDto> getProductQuantitiesById(int productId) {
        // Product 객체를 가져옴(orderDetails 필드(list)를 mybatis가 알아서 채워옴)
        Product product = productRepository
                .findProductWithQuantities(productId);
        // 만약에 A entity가 B를 가지고 있고
        // B entity가 A를 가지고 있을 수 있음 (양방향)
        // a.getB().getA().getB().getA()...... -> 자바에서는 문제가 안됨
        // -> 양방향설정을 왠만하면 쓰지 말자.

        // 옵셔널이 아니라서 null 체크를 해준다
        // product가 null 이거나, 필드에 있는 List<OrderDetail>이 null이면
        if(product == null || product.getOrderDetails() == null) {
            return List.of(); // 비어있는 리스트 리턴
        }

        List<ProductQuantityResDto> resultData = new ArrayList<>();
        // stream Api 사용하는 버전
        resultData = product.getOrderDetails() // List<OrderDetail>
                .stream() // Stream<OrderDetail>
                .map(od -> new ProductQuantityResDto(
                        // od.getProduct() -> xml에 정의하지 않았기 때문에 null(단방향)
                        product.getName(),
                        product.getPrice(),
                        od.getQuantity()
                )) // Stream<ProductQuantityResDto>
                .collect(Collectors.toList()); // List<ProductQuantityResDto>

        // for문을 사용하는 버전
//        for(OrderDetail od : product.getOrderDetails()) {
//            ProductQuantityResDto dto = new ProductQuantityResDto(
//                    product.getName(),
//                    product.getPrice(),
//                    od.getQuantity()
//            );
//            resultData.add(dto);
//        }
        
        return resultData;
    }

    // dto-req,res 2가지
    // req: nameKeyword, minPrice, maxPrice
    // res: id가 필요하면 id까지 dto에 작성하여 리턴한다.
    public List<SearchProductResDto> searchDetailProducts(
            SearchProductReqDto dto
    ) {
        List<Product> products = productRepository.searchDetailProducts(
                dto.getNameKeyword(),
                dto.getMinPrice(),
                dto.getMaxPrice()
        );

        if(products == null || products.isEmpty()) {
            throw new ProductNotFoundException("조건에 맞는 상품이 없습니다.");
        }

        List<SearchProductResDto> dtos = new ArrayList<>();
        // 1. stream api 사용하는 방법
        dtos = products.stream()
                .map(p -> new SearchProductResDto(
                        p.getName(),
                        p.getPrice()
                ))
                .collect(Collectors.toList());
        // 2. for문 사용하는 방법
//        for(Product p : products) {
//            SearchProductResDto resDto
//                    = new SearchProductResDto(p.getName(), p.getPrice());
//            dtos.add(resDto);
//        }
        return dtos;
    }

}
