package com.koreait.spring_boot_study.repository;

import com.koreait.spring_boot_study.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductJdbcRepo {

    // DB 경로 or 비밀번호와 같이 민감한 정보들을 소스코드로 노출되지 않게
    // yaml에 적어둔 DB 설정값을 스프링이 자동으로 읽어서
    // 그 값을 가진 DataSource 객체를 자동으로 만들어 Bean으로 등록해준다.
    private final DataSource dataSource;

    @Autowired
    public ProductJdbcRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> findAllProducts() {
        // 리턴해줄 List
        List<Product> products = new ArrayList<>();
        // db로 sql 전송 / 응답받기...

        // DB와 실제 연결을 수행하는 객체
        Connection conn = null;
        // Connection의 필드로 주입되어서 DB로 전송될 sql객체
        PreparedStatement ps = null;
        // DB에서 가져온 데이터를 자바에서 읽기 좋은 형태(자바객체)로 제공하는 객체
        // select 할때만 필요하다! -> 테이블을 결과로 받을때만 필요
        ResultSet rs = null;


        return products;
    }

}
