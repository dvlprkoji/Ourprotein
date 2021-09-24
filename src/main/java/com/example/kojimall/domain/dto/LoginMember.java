package com.example.kojimall.domain.dto;

import com.example.kojimall.domain.entity.Code;
import com.example.kojimall.domain.entity.Member;
import lombok.Data;

@Data
public class LoginMember {
    private Long memberId;
    private Code loginType;
    private Code memberRole;

    public LoginMember(Long id, Code loginType, Code role) {
        memberId = id;
        this.loginType = loginType;
        memberRole = role;
    }
}
