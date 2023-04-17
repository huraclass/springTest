package com.example.springtest.service;

import com.example.springtest.domain.LoginForm;
import com.example.springtest.domain.Member;
import com.example.springtest.mapper.LoginMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final LoginMapper mapper;

    @Override
    public void signUp(LoginForm loginForm) {
        mapper.signUp(new Member(loginForm.getLoginId(), loginForm.getName(), loginForm.getPassword()));
    }

    @Override
    public Member login(LoginForm loginForm) {
        return mapper.login(loginForm);
    }
}
