package com.example.kojimall.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OAuthRegisterForm {

    public Integer id;

    @NotEmpty(message = "Full Name is a must")
    public String name;
    @NotEmpty(message = "city is a must")
    public String city;
    @NotEmpty(message = "street is a must")
    public String street;
    @NotEmpty(message = "zipcode is a must")
    public String zipcode;


    public OAuthRegisterForm(Integer id) {
        this.id = id;
    }
}
