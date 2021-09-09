package com.example.kojimall.service;

import com.example.kojimall.domain.*;
import com.example.kojimall.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.naming.Binding;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(Member member) {
        Code memberCode = memberRepository.getCode("M0001");
        member.setMemberRole(memberCode);
        member.setSubYn("N");
        memberRepository.save(member);
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



    public Member toMember(RegisterForm registerForm) {
        Member member = new Member();
        member.setName(registerForm.getName());
        member.setEmail(registerForm.getEmail());
        member.setPassword(registerForm.getPassword());

        Address address = new Address(registerForm.getCity(), registerForm.getStreet(), registerForm.getZipcode());
        member.setAddress(address);

        member.setModDate(LocalDateTime.now());
        member.setRegDate(LocalDateTime.now());

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

    public Member login(LoginForm loginForm, BindingResult bindingResult) {
        List<Member> loginMember = memberRepository.getLoginMember(loginForm);
        if (loginMember.isEmpty()) {
            System.out.println("No Such Member Exist");
            return null;
        }
        else{
            System.out.println("loginMember = " + loginMember);
        }
        return loginMember.get(0);
    }

    public void update(Member member, RegisterForm registerForm) {
        memberRepository.update(member.getId(), registerForm);
    }

}
