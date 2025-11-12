package com.koreait.spring_boot_study.controller;

// Controller - 클라이언트(브라우저)와 서버(스프링부트) 사
// 이에 데이터를 주고받는 진입점
// 스프링에서 컨트롤러는 두가지 유형이 존재 
// 1.Controller - html(웹페이지)파일을 반환하는 컨트롤러 - 서버사이드렌더링(SSR)
// 2.REST Controller - JSON, 문자열 등의 데이터들만 반환하는 컨트롤러 - 클라이언트사이드렌더링(CSR)

import com.koreait.spring_boot_study.model.Hello;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudyController {
    // 인터넷 통신(HTTP 통신) - 웹에서 클라이언트와 서버가 데이터를 주고받는 규칙
    // 1. 한번 요청하면, 한번 응답한다.
    // 요청/응답 - 객체(header, body로 이루어져 있음)
    // 2. 요청의 경우, 메서드(방법)
    // - GET 요청: 자원 조회요청 - body가 없음, url에 쿼리스트링으로 데이터 전달
    // - POST 요청: 자원 생성요청
    // - DELETE 요청: 자원 삭제요청
    // - PUT 요청: 자원 전체수정 요청
    // - PATCH 요청: 자원 일부수정 요청
    // 톰캣서버(8080포트) + 로컬 -> localhost:8080(서버주소)
    // localhost:8080/hello -> 접속(GET요청)하면, helloPage 컨트롤러가 실행됨.
    @GetMapping("/hello") // hello라는 경로로 get요청이 들어오면 실행하세요
    public String helloPage(Model model) {
        // Model -> html에 데이터를 전달해주는 자바객체
        System.out.println("hello 컨트롤러 수신");
        // Hello 객체 생성
        Hello hello = Hello.builder()
                .hello1("데이터1")
                .hello2("데이터2")
                .build();

        // hello 객체를 "hello"이름으로 html에 전달
        model.addAttribute("hello", hello);

        // templetes 경로안에 hello.html을 찾아서 클라이언트에 보내줘라
        return "hello";
    }
}
