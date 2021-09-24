package com.example.kojimall.controller;

import com.example.kojimall.domain.SessionConst;
import com.example.kojimall.domain.dto.LoginMember;
import com.example.kojimall.domain.entity.Member;
import com.example.kojimall.service.CodeService;
import com.example.kojimall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final CodeService codeService;

    @GetMapping("/")
    public String home(HttpServletRequest request,
                       @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                       @SessionAttribute(name = SessionConst.LOGIN_TYPE, required = false) LoginMember loginMember,
                       Model model) {
        if (member == null) {
            return "index";
        }
        model.addAttribute("loginMember", loginMember);
        return "index";
    }


}
