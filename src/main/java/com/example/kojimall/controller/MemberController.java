package com.example.kojimall.controller;

import com.example.kojimall.common.CommonUtils;
import com.example.kojimall.common.SessionConst;
import com.example.kojimall.domain.LoginForm;
import com.example.kojimall.domain.Member;
import com.example.kojimall.domain.RegisterForm;
import com.example.kojimall.domain.UpdateForm;
import com.example.kojimall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final CommonUtils commonUtils;
    private final MemberService memberService;


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("form", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request,
                        @Valid @ModelAttribute("form") LoginForm loginForm,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL) {
        commonUtils.printParams(request);
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        if (bindingResult.hasErrors()) {
            return "login";
        }
        Member loginMember = memberService.login(loginForm, bindingResult);
        if (loginMember == null) {
            bindingResult.addError(new ObjectError("loginForm", "No Such Member Exist"));
            return "login";
        }
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        System.out.println("redirectURL = " + redirectURL);

        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("form", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest request, Model model, @Valid @ModelAttribute("form") RegisterForm registerForm, BindingResult bindingResult) {
        commonUtils.printParams(request);
        memberService.checkRegisterForm(registerForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register";
        }
        Member member = memberService.toMember(registerForm);
        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String myPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member, Model model) {
        if (member == null) {
            return "redirect:/login";
        }

        RegisterForm registerForm = memberService.toRegisterForm(member);
        model.addAttribute("form", registerForm);
        model.addAttribute("loginMember", member);
        return "mypage";
    }

    @PostMapping("/mypage")
    public String myPage(HttpServletRequest request,
                         @Valid @ModelAttribute("form") RegisterForm registerForm,
                         BindingResult bindingResult,
                         @SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member,
                         Model model) {

        commonUtils.printParams(request);
        memberService.checkRegisterForm(registerForm, bindingResult);
        System.out.println("checked");
        if (bindingResult.hasErrors()) {
            System.out.println("error ocurred");
            model.addAttribute("loginMember", member);
            return "mypage";
        }
        model.addAttribute("loginMember", member);
        memberService.update(member, registerForm);
        return "redirect:/";
    }
}
