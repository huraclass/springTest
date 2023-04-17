package com.example.springtest.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoginForm {
    private String loginId;
    private String name;
    private String password;
}
