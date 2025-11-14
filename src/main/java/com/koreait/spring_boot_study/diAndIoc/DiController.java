package com.koreait.spring_boot_study.diAndIoc;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DiController {
    // Controller : 요청수신 / 응답송신관련 코드작성
    // -> Service : 비즈니스(핵심)로직 / 트랜잭션관리
    // -> Repository : DB연결 / DB와 관련된 코드작성

    @GetMapping("/di")
    public ResponseEntity<?> diTest() {
        // diTest() 컨트롤러는 DiService 객체에 의존하고 있다.
        // DiService는 DiRepository 객체에 의존하고 있다.
        // 이 의존성을 내가 직접 코드로 컨트롤 하고 있다 -> DI를 직접하고 있다.

        // getInstance() 를 직접 호출함으로서,
        // 내가 직접 객체를 new하는 효과 -> 객체 생성도 직접 컨트롤 하고 있다.
        DiRepository diRepository = DiRepository.getInstance();
        DiService diService = DiService.getInstance(diRepository);

        int totalScore = diService.getTotal();
        double averageScore = diService.getAverage();

        Map<String, Object> resMap = Map.of(
                "총점", totalScore,
                "평균", averageScore
        );

        return ResponseEntity.ok(resMap);
    }
}
