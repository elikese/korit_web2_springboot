package com.koreait.spring_boot_study.repository.mapper;

import com.koreait.spring_boot_study.entity.Product;
import com.koreait.spring_boot_study.model.Top3SellingProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
// xml파일과 1:1 매핑되는 자바파일
// xml을 통해 db에서 가져온 결과(rs)를 자바객체로 가져오는 심부름역할
@Mapper
public interface ProductMapper { 
    /*
    1. conn, ps, rs try-catch-finally
    -> 이런코드들이 통째로 보일러 플레이트 코드다.
    -> 이런코드는 자동완성이 되었으면 좋겠다. (캡슐화 시켜버림)
    : 개발자는 sql만 신경썼으면 좋겠다.

    2. sql을 String자료형으로 작성했었음
    -> 자바랑 sql은 독립적인데 왜 java코드로 작성해야하는가?
    -> sql이 길어지면, java 코드가 어지럽혀진다. (java와 분리)
    : java파일말고, xml로 따로 분리시키겠다.

    3. jdbc에서 사용하던 rsToProduct() 메서드 -> 자동으로 지원하겠다.
    객체간 참조(그래프탐색)을 지원하겠다.
    db의 테이블과 1:1 대응되는 것이 entity -> fk컬럼을 id필드로 가지고 있음
    객체지향적(그래프탐색) entity -> fk컬럼을 객체자체를 필드로 가지고 있음(연관관계 설정)
    */

    // 1. 다건조회(전체조회)
    List<Product> findAllProducts();
    // 2. 단건조회(상품 하나만 조회)
    String findProductNameById(int id);
    // 상품 추가
    int insertProduct(String name, int price);
    // 단건 삭제
    int deleteProductById(int id);
    // 단건 업데이트
    int updateProduct(int id, String name, int price);

    // join 결과를 받아옴
    // 판매량기준 top3 받아오자!
    List<Top3SellingProduct> findTop3SellingProducts();
}
