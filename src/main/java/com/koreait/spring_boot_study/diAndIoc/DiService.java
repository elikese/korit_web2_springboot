package com.koreait.spring_boot_study.diAndIoc;

import java.util.List;

// 실습) DiService에 싱글톤 패턴을 적용시켜 주세요
public class DiService {

    private static DiService instance;
    private DiRepository diRepository;

    // 생성자 호출쪽에서 DiRepo- 를 주입
    private DiService(DiRepository diRepository) {
        this.diRepository = diRepository;
    }

    // getInstance를 호출쪽에서 DiRepo- 를 주입
    public static DiService getInstance(DiRepository diRepository) {
        if(instance == null) {
            instance = new DiService(diRepository);
        }
        return instance;
    }
    
    // 총점 구하는 메서드
    public int getTotal() {
        List<Integer> scores = diRepository.getScores();
        int total = 0;
        for(Integer score : scores) {
            total += score;
        }
        return total;
    }
    
    // 평균을 구하는 메서드
    public double getAverage() {
        List<Integer> scores = diRepository.getScores();
        double avg = (double) getTotal() / scores.size();
        return avg;
    }
    
}
