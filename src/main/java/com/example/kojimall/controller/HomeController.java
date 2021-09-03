package com.example.kojimall.controller;

import com.example.kojimall.common.SessionConst;
import com.example.kojimall.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, Model model) {
        if (member == null) {
            return "index";
        }
        model.addAttribute("loginMember", member);
        return "index";
    }


}
