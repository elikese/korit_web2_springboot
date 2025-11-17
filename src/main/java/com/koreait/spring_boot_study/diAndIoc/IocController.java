package com.koreait.spring_boot_study.diAndIoc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.ApplicationContext; // IOC 컨테이너 패키지
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class IocController {

    // @Autowired
    private IocService iocService;
    
    // 어플리케이션 컨텍스트 -> IOC 컨테이너
    // run()하면 가장 먼저 생성되는 싱글톤 객체!
    private ApplicationContext context;
    private ObjectMapper objectMapper; // 외부라이브러리 bean

    @Autowired // 권장
    public IocController(
            IocService iocService,
            ApplicationContext context,
            ObjectMapper objectMapper
    ) {
        this.iocService = iocService;
        this.context = context;
        this.objectMapper = objectMapper;
    }

    // localhost:8080/ioc
    @GetMapping("/ioc")
    public ResponseEntity<?> diTest() throws JsonProcessingException {
        int total = iocService.getTotal();
        double average = iocService.getAverage();
        Map<String, Object> resData = Map.of(
                "total", total,
                "average", average
        );

        // 자바객체 -> JSON(문자열) by Jackson 라이브러리(ObjectMapper)
        String jsonData = objectMapper.writeValueAsString(resData);

        // 외부에서 들어오거나 나가는데이터 타입: 문자열 취급을 한다.
        // raw: 문자열로 보겠다, JSON: JSON 포맷으로 읽겠다
        return ResponseEntity
                .status(HttpStatus.OK) // 응답코드(헤더)
                .contentType(MediaType.APPLICATION_JSON) // body의 자료구조지정(헤더)
                .body(jsonData); // body 데이터(바디)
    }

    @GetMapping("/beans")
    public ResponseEntity<?> showBeans() {
        // 컴포넌트 스캔을 마친 후, 등록된(컨테이너에 담겨있는)
        // 싱글톤 객체들의 이름들을 배열로 리턴
        String[] beans = context.getBeanDefinitionNames();
        return ResponseEntity.ok(beans);
    }

    // 우리가 직접 선언하지 않은 클래스의 객체는 bean으로 만들 수 없는가?
    // 외부 라이브러리 사용시! 유틸리티 클래스의 객체를 bean으로 만들고 싶을 때
    // -> @Configuration



}
