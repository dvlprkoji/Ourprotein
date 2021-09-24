package com.example.kojimall.repository.OAuth;

import com.example.kojimall.domain.entity.Member;
import com.example.kojimall.domain.entity.OAuth;

import java.util.Optional;

public interface OAuthRepositoryCustom {
    Boolean checkRegister();
    OAuth findOAuthByKaKao(Integer id);

    Member findMemberByOAuth(OAuth findOAuth);
}
