package com.example.kojimall.controller;

import com.example.kojimall.common.CommonUtils;
import com.example.kojimall.domain.SessionConst;
import com.example.kojimall.domain.dto.*;
import com.example.kojimall.domain.entity.Code;
import com.example.kojimall.domain.entity.Member;
import com.example.kojimall.domain.entity.OAuth;
import com.example.kojimall.service.CodeService;
import com.example.kojimall.service.MemberService;
import com.example.kojimall.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Optional;

import static com.example.kojimall.domain.SessionConst.KAKAO_TOKEN;
import static com.example.kojimall.domain.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final CommonUtils commonUtils;
    private final CodeService codeService;
    private final MemberService memberService;
    private final OAuthService oAuthService;



    @GetMapping("/oauth/kakao/callback")
    @ResponseBody
    public void kakaoCallback(@RequestParam(name="code") String code) {

        OAuthToken kakaoToken = oAuthService.getRegisterKakaoToken(code);
        KakaoProfile kakaoProfile = oAuthService.getKakaoProfile(kakaoToken);
        System.out.println("kakaoProfile = " + kakaoProfile.getProperties());
    }

    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("form", new LoginForm());
        return "login";
    }

    @GetMapping("/login/kakao")
    public String loginKakao(@RequestParam String code,
                                Model model,
                                HttpServletRequest request) {
        OAuthToken kakaoToken = oAuthService.getLoginKakaoToken(code);
        KakaoProfile kakaoProfile = oAuthService.getKakaoProfile(kakaoToken);
        OAuth findOAuth = oAuthService.findOAuthByKakaoId(kakaoProfile.getId());
        if (findOAuth == null) {
            model.addAttribute("errorMessage", "Your Account Doesn't Exists");
            model.addAttribute("form", new LoginForm());
        }
        Member member = oAuthService.findMemberByOAuth(findOAuth);
        LoginMember loginMember = memberService.toLoginMember(member, codeService.getKakaoLoginCode());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        session.setAttribute(SessionConst.LOGIN_TYPE, loginMember);
        model.addAttribute("loginMember", loginMember);

        return "redirect:/";
    }


    @PostMapping("/login")
    public String login(HttpServletRequest request,
                        Model model,
                        @Valid @ModelAttribute("form") LoginForm loginForm,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        Member member = memberService.login(loginForm, bindingResult).get();

        if (member == null) {
            bindingResult.addError(new ObjectError("loginForm", "No Such Member Exist"));
            return "login";
        }

        HttpSession memberSession = request.getSession();
        memberSession.setAttribute(SessionConst.LOGIN_MEMBER, member);
        HttpSession loginMemberSession = request.getSession();
        loginMemberSession.setAttribute(SessionConst.LOGIN_TYPE, memberService.toLoginMember(member,codeService.getEmailLoginCode()));

        model.addAttribute("loginMember", memberService.toLoginMember(member, codeService.getEmailLoginCode()));
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

    @GetMapping("/logout/kakao")
    public String logoutKakao(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "signup";
    }

    @GetMapping("/register/kakao")
    public String registerKakao(@RequestParam String code,
                                Model model,
                                HttpServletRequest request) {
        OAuthToken kakaoToken = oAuthService.getRegisterKakaoToken(code);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(KAKAO_TOKEN,kakaoToken);
        KakaoProfile kakaoProfile = oAuthService.getKakaoProfile(kakaoToken);
        if (oAuthService.checkRegister(kakaoProfile)) {
            model.addAttribute("errorMessage", "Your Account Already Exists");
            model.addAttribute("form", new LoginForm());

            return "login";
        }
        model.addAttribute("form", new OAuthRegisterForm(kakaoProfile.getId()));
        return "oauthregister";
    }


    @PostMapping("/register/oauth")
    public String registerKakao(HttpServletRequest request,
                                Model model,
                                @ModelAttribute OAuthRegisterForm form,
                                @SessionAttribute(name = KAKAO_TOKEN) OAuthToken kakaoToken) {
        Member member = memberService.toMember(form);
        Code memberCode = codeService.getMemberCode();
        memberService.join(member, memberCode);
        OAuth oAuth = oAuthService.joinOAuth(member);
        oAuthService.joinKakao(form.getId(), oAuth);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        LoginMember loginMember = memberService.toLoginMember(member, codeService.getKakaoLoginCode());
        session.setAttribute(SessionConst.LOGIN_TYPE, loginMember);

        model.addAttribute("loginMember", loginMember);
        return "redirect:/";
    }

    @GetMapping("/register/normal")
    public String registerNormal(Model model) {

        model.addAttribute("form", new RegisterForm());
        return "register";
    }



    @PostMapping("/register")
    public String register(HttpServletRequest request, Model model, @Valid @ModelAttribute("form") RegisterForm registerForm, BindingResult bindingResult) {
        memberService.checkRegisterForm(registerForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register";
        }
        Member member = memberService.toMember(registerForm);
        Code memberCode = codeService.getMemberCode();
        memberService.join(member, memberCode);

        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String myPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member, Model model) {
        if (member == null) {
            return "redirect:/login";
        }

        RegisterForm registerForm = memberService.toRegisterForm(member);
        model.addAttribute("form", registerForm);
        model.addAttribute("loginMember", memberService.toLoginMember(member, codeService.getEmailLoginCode()));
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
        model.addAttribute("loginMember", memberService.toLoginMember(member, codeService.getEmailLoginCode()));
        if (bindingResult.hasErrors()) {
            System.out.println("error ocurred");
            return "mypage";
        }
        memberService.update(member, registerForm);
        return "redirect:/";
    }
}
