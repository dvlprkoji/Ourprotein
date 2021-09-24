package com.example.kojimall.repository.Member;

import com.example.kojimall.domain.dto.LoginForm;
import com.example.kojimall.domain.entity.Member;
import com.example.kojimall.domain.entity.OAuth;
import com.example.kojimall.domain.entity.QMember;
import com.example.kojimall.domain.entity.QOAuth;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static com.example.kojimall.domain.entity.QMember.*;
import static com.example.kojimall.domain.entity.QMember.member;
import static com.example.kojimall.domain.entity.QOAuth.oAuth;

public class JpaMemberRepositoryImpl implements JpaMemberRepositoryCustom {

    private final JPAQueryFactory qf;

    public JpaMemberRepositoryImpl(EntityManager em) {
        this.qf = new JPAQueryFactory(em);
    }

    public Optional<Member> getLoginMember(LoginForm loginForm) {
        return Optional.ofNullable(qf.
                selectFrom(member).
                where(
                        emailEq(loginForm.getEmail()),
                        passwordEq(loginForm.getPassword())).
                fetchOne());
    }


    private Predicate emailEq(String email) {
        return email.isEmpty() ? null : member.email.eq(email);
    }

    private Predicate passwordEq(String password) {
        return password.isEmpty() ? null : member.password.eq(password);
    }

}
