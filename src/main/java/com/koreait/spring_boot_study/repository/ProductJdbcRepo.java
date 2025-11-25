package com.koreait.spring_boot_study.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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

}
