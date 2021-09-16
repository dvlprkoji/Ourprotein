package com.example.kojimall.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {
    @NotEmpty(message = "Email is a must")
    public String email;
    @NotEmpty(message = "Password is a must")
    public String password;
}
