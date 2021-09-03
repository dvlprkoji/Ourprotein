package com.example.kojimall.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateForm {


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