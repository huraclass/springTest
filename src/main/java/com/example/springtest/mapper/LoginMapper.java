package com.example.springtest.mapper;

import com.example.springtest.domain.LoginForm;
import com.example.springtest.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    void signUp(Member loginForm);

    Member login(LoginForm loginForm);
}
