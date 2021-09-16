package com.example.kojimall.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterForm {

    @NotEmpty(message = "Full Name is a must")
    public String name;
    @NotEmpty(message = "email is a must")
    public String email;
    @NotEmpty(message = "password is a must")
    public String password;
    @NotEmpty(message = "city is a must")
    public String city;
    @NotEmpty(message = "street is a must")
    public String street;
    @NotEmpty(message = "zipcode is a must")
    public String zipcode;
}
