package com.koreait.spring_boot_study.repository.mapper;

import com.koreait.spring_boot_study.entity.Product;
import com.koreait.spring_boot_study.model.Top3SellingProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    --- mybatis 내부구현에 대한 간략한 이해
    mapper(interface: 추상체) --- dynamicProxy(mybatis가 알아서) --- xml(실제 구현체)
    1. 서비스는 mapper interface만 알고있음, 주입받고 있다.
    2. 실제로 Ioc컨테이너에서 주입해주는 것은 mapper 인터페이스가아니라, dynamicProxy객체
    3. dynamicProxy객체를 mybatis가 xml을 보고 생성 & bean 등록을 함.
    */

    // 1. 다건조회(전체조회)
    List<Product> findAllProducts();
    // 2. 단건조회(상품 하나만 조회)
    String findProductNameById(int id);

    // 상품 추가
    int insertProduct(
            @Param("name") String name,
            @Param("price") int price
    ); // @Param -> xml에서 매개변수이름을 전달할때 사용
    // 매개변수들을 HashMap형태로 가져가게 됨.
    // 우리가 @Param에 적어주는 것은 key값
    // xml에서는 해당 key값을 적어줘서 value값들을 동적으로 처리
    // Param을 적어주지 않으면, 컴파일러 옵션에 따라서 작동 할 때도 있고, 안될때도 있다.
    // -> 매개변수가 2개 이상일 경우, 적어주는걸 권장

    // 단건 삭제
    int deleteProductById(int id);

    // 단건 업데이트
    int updateProduct(
            @Param("id") int id,
            @Param("name") String name,
            @Param("price") int price
    );

    // join 결과를 받아옴
    // 판매량기준 top3 받아오자!
    List<Top3SellingProduct> findTop3SellingProducts();

    // productId로 판매량까지 같이 조회
    Product findProductWithQuantities(int productId);

}
