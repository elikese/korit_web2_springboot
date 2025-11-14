package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// @Controller가 아닌 @RestController
@RestController
// html을 리턴하는게 x
// 데이터(객체, 문자열...) 리턴
@Slf4j
@RequestMapping("/study") // 클래스 내부 컨트롤러의 공통 path url 지정 가능
public class StudyRestController1 {

    // localhost:8080/study/test1
    @GetMapping("/test1")
    public String test1() {
//        System.out.println("test1 컨트롤러 수신");
        log.info("test1 컨트롤러 수신");
        return "양호합니다";
    }

    // GET 요청의 경우 쿼리스트링으로 데이터를 전달할수 있다
    // 클라이언트(브라우저) -> 서버로 전달
    // localhost:8080/study/test2?name="홍길동"
    // http://서버주소/경로?name="홍길동"
    @GetMapping("/test2")
    public String test2(@RequestParam("name") String str) {
        // RequestParam은 쿼리스트링의 key와 매개변수의 이름이 같으면 생략가능
        log.info("test2 컨트롤러 수신");
        log.info("들어온 데이터: {}", str);
        return str;
    }

    // 파라미터 2개 & RequestParam 생략
    // localhost:8080/study/test3?name=홍길동&age=20
    @GetMapping("/test3")
    public String test3(String name, Integer age) {
        // RequestParam은 쿼리스트링의 key와 매개변수의 이름이 같으면 생략가능
        // 숫자의 경우 알아서 매개변수 타입으로 변환(파싱) 해준다.
        log.info("test3 컨트롤러 수신");
        log.info("들어온 데이터: {}, {}", name, age);
        return "수신성공";
    }

    // 객체 리턴이 가능하다.
    @GetMapping("/test4")
    public List<String> test4() {
        log.info("test4 컨트롤러 수신");
        List<String> names = List.of("홍길동", "김길동", "고길동");
        return names;
    }

    // 객체 리턴이 가능하다2
    // JSON - 서버와 클라이언트(브라우저, 포스트맨)사이에 주고받는 
    // 웹 데이터 표준형식 중 하나(XML..등)
    // 자바의 Map과 유사하게 생겼음.
    // 자바의 객체도 전송이 가능하다 -> (자바객체 -> JSON -> JavaScript 객체)
    @GetMapping("/test5")
    public List<Map<String, Object>> test5() {
        log.info("test5 컨트롤러 수신");
        List<Map<String, Object>> myData = new ArrayList<>();
        /*
        [
            {key1: value1},
            {key2: value2},
            {key3: value3}
        ]
        */
        Map<String, Object> data1 = Map.of(
                "name", "홍길동",
                "age", 20
        );
        Map<String, Object> data2 = Map.of(
                "name", "김길동",
                "age", 22
        );
        myData.add(data1);
        myData.add(data2);
        return myData;
    }

    // localhost:8080/study/test6/{id}
    @GetMapping("/test6/{id}")
    public Map<String, Object> getStudent(@PathVariable("id") int id) {
        List<Student> studentList = List.of(
                new Student(1, "피카츄"),
                new Student(2, "라이츄"),
                new Student(3, "파이리"),
                new Student(4, "꼬부기")
        );
        // 있는 번호인지 검증
        // 없으면 없다고 return을 해줘야함.
        Student target = null;
        for(Student student : studentList) {
            if(student.getId() == id) {
                target = student;
            }
        }

        if(target == null) {
            return Map.of("error", "해당 id의 학생은 존재하지 않습니다");
        }

        return Map.of("success", target);
    }

    // localhost:8080/study/test7?id=1&name=피카츄
    @GetMapping("/test7")
    public String test7(@ModelAttribute Student student) {
        // 쿼리스트링으로 데이터를 받을때, 객체로 받으면 안될까? -> ModelAttribute
        // 참고) Jackson 라이브러리가 요청을 가로채서 바꾼뒤에 매개변수에 할당해준다.
        // 1. 쿼리스트링의 key들과 클래스의 필드명이 동일해야한다.
        // 2. 해당 클래스에 생성자 or setter가 정의되어 있어야 한다.
        log.info("들어온 데이터: {}", student);
        return "성공";
    }


}
