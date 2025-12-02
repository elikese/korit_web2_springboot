package com.koreait.spring_boot_study.controller;

import com.koreait.spring_boot_study.dto.req.AddPostReqDto;
import com.koreait.spring_boot_study.dto.req.StudyReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/study3")
@Slf4j
public class StudyRestController3 {
    /*
    HTTP 요청 메서드(GET, POST, PUT, PATCH, DELETE)
    GET 요청을 제외한 모든 요청메서드에는 body가 존재한다.
    body의 특징 - url과 무관하게 데이터를 송신가능 -> 보안이 좋다.
    JSON을 body에 담아서 송신
    */

    // localhost:8080/study3/test1
    /*
    {
        "data1" : "데이터1",
        "data2" : "데이터2"
    }

    */
    @PostMapping("/test1")
    public String test1(@RequestBody Map<String, Object> data) {
        // @RequestBody -> body에 작성되어있는 json 데이터를 알아서 자바객체로 바꿔준다.
        // 참고) Jackson 라이브러리가 개입해서 자동으로 변환해준다.
        log.info("test1 컨트롤러 수신");
        log.info("들어온 데이터: {}", data);

        return "성공";
    }

    @PostMapping("/test2")
    public String test2(@RequestBody StudyReqDto dto) {
        // 내가 정의한 클래스의 객체로 데이터를 받을때
        // 주의) 필드명과 JSON key 이름이 동일해야 한다.
        log.info("test2 컨트롤러 수신");
        log.info("들어온 데이터: {}", dto);

        return "성공";
    }

    // 실습) test3라는 이름으로 AddPostReqDto 타입의 데이터를 수신해주세요.
    // PostMapping
    @PostMapping("/test3")
    public ResponseEntity<?> test3(@RequestBody AddPostReqDto dto) {
        // ResponseEntity: HTTP 응답을 자바에서 커스터마이징하기 편하게 만든 클래스
        // 제너릭타입을 받는다 -> body에 들어가는 데이터타입
        // HTTP 상태코드, body, header등을 쉽게 지정할 수 있다.
        /*
            {
                "title" : "제목1",
                "content" : "내용1"
            }
        */
        log.info("test3 컨트롤러 수신");
        log.info("들어온 데이터: {}", dto);

        /*
        HTTP 상태코드
        200 -> 성공
        400 -> 요청을 잘못했다.
        500 -> 서버가 잘못했다.
        200: 성공 OK, 201: 생성성공 CREATED
        400: 잘못된요청 BAD_REQUEST, 401: 인증실패 UNAUTHORIZED
        403: 권한없음 FORBIDDEN, 404: 리소스가 없다(주소입력 잘못했음) NOT_FOUND
        500: 서버 내부 오류(코드문제, 예외처리 불량, DB에러가 서버까지 올라온경우)
        */
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("성공");
    }
    
    /*
    - 어차피 RequestBody로 id 전달하면 되는거아닌가?
    - 기술적으로는 RequestBody로 id까지 객체로 데이터를 받아서 코딩 가능
    - RESTful 설계
        - URL: "어떤 자원을 다룰것인가?"(식별) -> id
        - METHOD: "뭘 할건가?"(행위) -> put(수정)
        - body: "어떤 데이터로?"(내용) -> 내가보낸 dto로 바꿔줘
    */

    @PutMapping("/test4/{id}")
    public ResponseEntity<?> test4(@PathVariable int id, @RequestBody StudyReqDto dto) {
        log.info("test4 컨트롤러 수신");
        log.info("들어온 데이터: id={}, dto={}", id, dto);
        return ResponseEntity.ok("성공"); // 상태코드 OK + body
    }

    // PUT, PATCH -> 둘다 수정하겠다는 의미
    // PUT -> 들어온 dto데이터로 싹 다 바꾸겠다
    // PATCH -> 들어온 dto데이터 중 일부만 반영하겠다.


    // DELETE -> body쓸 수 있는데, 보통 안씀
    @DeleteMapping("/test5/{id}")
    public ResponseEntity<?> test5(@PathVariable int id) {

        return ResponseEntity.ok("삭제 완료!");
    }

}
