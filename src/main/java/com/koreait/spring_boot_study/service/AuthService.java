package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.dto.req.SignUpReqDto;
import com.koreait.spring_boot_study.entity.User;
import com.koreait.spring_boot_study.exception.UserException;
import com.koreait.spring_boot_study.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// final 필드만 초기화하는 생성자
// fianl 필드에 대해서 자동으로 autowired 된다.(다른 생성자가 없을 때)
public class AuthService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;

    public void signUp(SignUpReqDto dto) {
        // 1. 아이디, 이메일 중복검사
        boolean isDuplicatedUsername
                = userMapper
                .getUserByUsername(dto.getUsername()).isPresent();
                // Optional안에 값이 있으면 true
        if(isDuplicatedUsername) {
            throw new UserException(
                    "이미 존재하는 아이디 입니다.", HttpStatus.CONFLICT
            );
        }

        boolean isDuplicatedEmail
                = userMapper.getUserByEmail(dto.getEmail()).isPresent();
        if(isDuplicatedEmail) {
            throw new UserException(
                    "이미 존재하는 이메일입니다.", HttpStatus.CONFLICT
            );
        }
        // 2. dto -> entity
        User user = dto.toEntity();
        // password를 암호화해서 set해줘야 한다.
        user.setPassword(encoder.encode(dto.getPassword()));

        // 3. db에 저장
        int successCount = userMapper.addUser(user);
        if(successCount <= 0) {
            throw new UserException(
                    "회원가입 중 에러가 발생하였습니다.",
                    HttpStatus.INTERNAL_SERVER_ERROR // 500
                    );
        }
    }

}
