package com.koreait.spring_boot_study.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// IOC 컨테이너에 직접 등록한 Bean들을 메서드형식으로 선언
public class AppConfig {
    // 컴포넌트 스캔 -> @Component, @Service, @Repo-...스캔
    // 그런데, 외부 라이브러리는 스캔범위 밖이다.

    // jackson 라이브러리의 ObjectMapper 클래스를
    // bean으로 만들어서 IOC 컨테이너에 보관!
    // bean으로 등록할 클래스는 반드시 상태가 없어야 한다.
    @Bean // bean 등록
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
}
