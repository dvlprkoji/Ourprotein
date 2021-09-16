package com.example.kojimall.domain.dto;

import lombok.Data;

@Data
public class LoginMember {
    private Long memberId = -1L;

    public LoginMember(Long id) {
        memberId = id;
    }
}
