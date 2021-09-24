package com.example.kojimall.repository.OAuth;

import com.example.kojimall.domain.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.example.kojimall.domain.entity.QKakao.*;
import static com.example.kojimall.domain.entity.QMember.member;
import static com.example.kojimall.domain.entity.QOAuth.*;

public class OAuthRepositoryImpl implements OAuthRepositoryCustom {

    private final JPAQueryFactory qf;

    public OAuthRepositoryImpl(EntityManager em) {
        qf = new JPAQueryFactory(em);
    }

    public Boolean checkRegister() {
        return true;
    }

    @Override
    public OAuth findOAuthByKaKao(Integer id) {
        return qf
                .selectFrom(oAuth)
                .join(oAuth.kakao, kakao)
                .where(kakao.kakaoId.eq(id))
                .fetchOne();
    }

    @Override
    public Member findMemberByOAuth(OAuth findOAuth) {
        return qf.
                select(member).
                from(oAuth).
                join(oAuth.member, member).
                where(oAuth.eq(findOAuth)).
                fetchOne();
    }
}
