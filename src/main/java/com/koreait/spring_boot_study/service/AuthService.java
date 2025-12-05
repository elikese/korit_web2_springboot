package com.koreait.spring_boot_study.service;

import com.koreait.spring_boot_study.dto.req.SignInReqDto;
import com.koreait.spring_boot_study.dto.req.SignUpReqDto;
import com.koreait.spring_boot_study.dto.res.SignInResDto;
import com.koreait.spring_boot_study.entity.User;
import com.koreait.spring_boot_study.exception.UserException;
import com.koreait.spring_boot_study.jwt.JwtUtil;
import com.koreait.spring_boot_study.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
// final 필드만 초기화하는 생성자
// fianl 필드에 대해서 자동으로 autowired 된다.(다른 생성자가 없을 때)
public class AuthService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;


    // User 객체만 가져오면 토큰 쌍으로 바꿔서 리턴
    private SignInResDto generateTokenPair(User user) {
        // 토큰에 담을 sub, extraClaims를 User로부터 추출
        String sub = String.valueOf(user.getUserId());

        Map<String, Object> extraClaims = Map.of(
                "role", user.getRole().getRoleName()
        );

        // TokenPair 생성
        String accessToken = jwtUtil.generateAccessToken(sub, extraClaims);
        String refreshToken = jwtUtil.generateRefreshToken(sub);

        return new SignInResDto(accessToken, refreshToken);
    }


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
    
    // 로그인
    public SignInResDto signIn(SignInReqDto dto) {
        // 실제 아이디가 있는지 검사
        User user = userMapper.getUserByUsername(dto.getUsername())
                .orElseThrow(() ->
                        new UserException("사용자 정보를 잘못 입력하셨습니다",
                                HttpStatus.BAD_REQUEST));

        // 비밀번호 확인
        // encoder.matches(평문암호, 암호화된암호) -> 맞으면 true
        if(!encoder.matches(dto.getPassword(), user.getPassword())){
            // 비밀번호 틀렸을때
            throw new UserException("사용자 정보를 잘못 입력하셨습니다."
                        , HttpStatus.BAD_REQUEST);
        }
        
        // id pw 모두 통과! -> 로그인시켜줘야한다(토큰발급)
        SignInResDto tokenPair = generateTokenPair(user); 
        
        // refresh토큰을 db에 저장 (나중에)

        return tokenPair;
    }


}
