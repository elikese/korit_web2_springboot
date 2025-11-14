package com.koreait.spring_boot_study.diAndIoc;

import java.util.List;

public class DiRepository {
    // 싱글톤패턴을 적용! -> 객체 하나만 생성해서 돌려쓰자!
    // 1. 생성자 외부접근을 private로 막는다.
    // 2. 자기 자신의 타입을 static 필드로 가진다.
    // 3. 외부접근이 가능한 static 메서드로 단 하나의 객체만 사용하게 설계

    // DB대용 데이터
    private List<Integer> scores = List.of(100, 90, 80, 70);

    // 자기자신의 타입을 static 필드로 가진다.(private -> 외부접근안됨)
    private static DiRepository instance;
    // DiRepository.instance (x)

    // private로 외부에서 생성자 호출을 막는다.
    private DiRepository() {}

    public static DiRepository getInstance() {
        if(instance == null) {
            instance = new DiRepository();
        }
        return instance;
    }

    public List<Integer> getScores() {
        return scores;
    }
}
