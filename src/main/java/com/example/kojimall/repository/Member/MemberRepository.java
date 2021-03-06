package com.example.kojimall.repository.Member;


import com.example.kojimall.domain.*;
import com.example.kojimall.domain.dto.LoginForm;
import com.example.kojimall.domain.dto.RegisterForm;
import com.example.kojimall.domain.entity.Code;
import com.example.kojimall.domain.entity.CodeKey;
import com.example.kojimall.domain.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository{

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public List<Member> getLoginMember(LoginForm loginForm) {
        TypedQuery<Member> query = em.createQuery("select m from Member m where m.email=:email and m.password=:password", Member.class);
        query.setParameter("email", loginForm.getEmail());
        query.setParameter("password", loginForm.getPassword());
        List<Member> loginMember = query.getResultList();
        return loginMember;
    }

    public void update(Long pk, RegisterForm registerForm) {
        Member member = em.find(Member.class, pk);
        member.setName(registerForm.getName());
        member.setEmail(registerForm.getEmail());
        member.setPassword(registerForm.getPassword());
        member.setAddress(new Address(
                                registerForm.getCity(),
                                registerForm.getStreet(),
                                registerForm.getZipcode()));
    }

    public Code getCode(String code) {
        return em.find(Code.class, new CodeKey("M0001", "MEM_TY"));
    }
}
