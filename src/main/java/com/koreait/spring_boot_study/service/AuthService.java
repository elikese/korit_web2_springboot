package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// final 필드만 초기화하는 생성자
// fianl 필드에 대해서 자동으로 autowired 된다.(다른 생성자가 없을 때)
public class AuthService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;



}
