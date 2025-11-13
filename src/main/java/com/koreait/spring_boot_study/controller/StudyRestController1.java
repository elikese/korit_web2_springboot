package com.koreait.spring_boot_study.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @Controller가 아닌 @RestController
@RestController
// html을 리턴하는게 x
// 데이터(객체, 문자열...) 리턴
@Slf4j
public class StudyRestController1 {

    // localhost:8080/study/test1
    @GetMapping("/study/test1")
    public String test1() {
//        System.out.println("test1 컨트롤러 수신");
        log.info("test1 컨트롤러 수신");
        return "양호합니다";
    }

    // GET 요청의 경우 쿼리스트링으로 데이터를 전달할수 있다
    // 클라이언트(브라우저) -> 서버로 전달
    // localhost:8080/study/test2?name="홍길동"
    // http://서버주소/경로?name="홍길동"
    @GetMapping("/study/test2")
    public String test2(@RequestParam("name") String str) {
        // RequestParam은 쿼리스트링의 key와 매개변수의 이름이 같으면 생략가능
        log.info("test2 컨트롤러 수신");
        log.info("들어온 데이터: {}", str);
        return str;
    }

    // 파라미터 2개 & RequestParam 생략
    // localhost:8080/study/test3?name=홍길동&age=20
    @GetMapping("/study/test3")
    public String test3(String name, Integer age) {
        // RequestParam은 쿼리스트링의 key와 매개변수의 이름이 같으면 생략가능
        // 숫자의 경우 알아서 매개변수 타입으로 변환(파싱) 해준다.
        log.info("test3 컨트롤러 수신");
        log.info("들어온 데이터: {}, {}", name, age);
        return "수신성공";
    }


}
