package com.example.springtest.controller;

import com.example.springtest.domain.LoginForm;
import com.example.springtest.domain.Member;
import com.example.springtest.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm() {
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm, HttpServletResponse response) {
        Member loginMember = loginService.login(loginForm);
        log.info("member : {}",loginMember);
        Cookie cookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        cookie.setMaxAge(60);
        response.addCookie(cookie);
        //response.addCookie(new Cookie("name", String.valueOf(loginMember.getName())));
        return "redirect:/boards";
    }

    @GetMapping("/signup")
    public String signUpForm() {
        return "login/signup";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute LoginForm loginForm) {
        loginService.signUp(loginForm);
        return "redirect:/login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        Cookie cookie = new Cookie("memberId", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "index";
    }
}
