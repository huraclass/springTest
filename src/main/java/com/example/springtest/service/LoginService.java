package com.example.springtest.service;


import com.example.springtest.domain.LoginForm;
import com.example.springtest.domain.Member;

public interface LoginService {

    void signUp(LoginForm loginForm);

    Member login(LoginForm loginForm);

}
