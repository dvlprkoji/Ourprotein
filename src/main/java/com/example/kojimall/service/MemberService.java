package com.example.kojimall.service;

import com.example.kojimall.domain.*;
import com.example.kojimall.domain.dto.*;
import com.example.kojimall.domain.entity.Code;
import com.example.kojimall.domain.entity.Member;
import com.example.kojimall.domain.entity.OAuth;
import com.example.kojimall.repository.CodeRepository;
import com.example.kojimall.repository.Member.JpaMemberRepository;
import com.example.kojimall.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.example.kojimall.domain.CodeVal.*;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JpaMemberRepository jpaMemberRepository;

    private final CodeRepository codeRepository;

    @Value("${oauth.kakao.client-id}")
    private String KAKAO_CLIENT_KEY;

    public void initMember() {
        Member admin = new Member();
        admin.setMemberRole(codeRepository.getCode(MEMBER_ADMIN));
        admin.setAddress(new Address("admin", "admin", "admin"));
        admin.setEmail("admin");
        admin.setPassword("wnlwnl123");
        admin.setName("admin");
        admin.setSubYn("Y");
        memberRepository.save(admin);

        Member member = new Member();
        member.setMemberRole(codeRepository.getCode(MEMBER_VIP));
        member.setAddress(new Address("seoul", "tongilro-555", "03647"));
        member.setEmail("koji4321@naver.com");
        member.setPassword("wnlwnl123");
        member.setName("AhnJaeHong");
        member.setSubYn("Y");
        memberRepository.save(member);

    }


//    public void join(Member member) {
//        Code memberCode = memberRepository.getCode("M0001");
//        member.setMemberRole(memberCode);
//        member.setSubYn("N");
//        memberRepository.save(member);
//    }

    public Long join(Member member, Code memberRole) {
        member.setMemberRole(memberRole);
        member.setSubYn("N");
        Member save = jpaMemberRepository.save(member);
        return save.getId();
    }

    public void checkLoginForm(LoginForm loginForm, BindingResult bindingResult) {
    }

    public void checkRegisterForm(RegisterForm registerForm, BindingResult bindingResult) {
        // password 검증
        if(registerForm.getPassword().length()<9){
            bindingResult.addError(new FieldError("registerForm","password","password should be at least 9 characters"));
        }
    }

    public void checkUpdateForm(UpdateForm updateForm, BindingResult bindingResult) {
        // password 검증
        if(updateForm.getPassword().length()<9){
            bindingResult.addError(new FieldError("registerForm","password","password should be at least 9 characters"));
        }
    }

    public LoginMember toLoginMember(Member member, Code loginType) {
        return new LoginMember(member.getId(), loginType, member.getMemberRole());
    }

    public Member toMember(RegisterForm registerForm) {
        Member member = new Member();
        member.setName(registerForm.getName());
        member.setEmail(registerForm.getEmail());
        member.setPassword(registerForm.getPassword());

        Address address = new Address(registerForm.getCity(), registerForm.getStreet(), registerForm.getZipcode());
        member.setAddress(address);


        return member;
    }

    public RegisterForm toRegisterForm(Member member) {
        RegisterForm registerForm = new RegisterForm();
        registerForm.setName(member.getName());
        registerForm.setEmail(member.getEmail());
        registerForm.setPassword(member.getPassword());
        registerForm.setCity(member.getAddress().getCity());
        registerForm.setStreet(member.getAddress().getStreet());
        registerForm.setZipcode(member.getAddress().getZipcode());

        return registerForm;
    }

    public Optional<Member> login(LoginForm loginForm, BindingResult bindingResult) {
        return jpaMemberRepository.getLoginMember(loginForm);
    }

    public void update(Member member, RegisterForm registerForm) {
        memberRepository.update(member.getId(), registerForm);
    }

    public Member toMember(OAuthRegisterForm form) {
        return new Member(form.getName(), new Address(form.city, form.street, form.zipcode));
    }

    public void disconnectKakao(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", KAKAO_CLIENT_KEY);
        params.add("logout_redirect_uri", "http://localhost:8080/logout/kakao");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/logout",
                HttpMethod.GET,
                httpEntity,
                String.class
        );
    }



}
