package com.koreait.spring_boot_study.repository.mapper;

import com.koreait.spring_boot_study.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    // 회원가입 - User 엔티티 전달
    int addUser(User user);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(int userId);

}
