package com.example.kojimall.repository.Member;

import com.example.kojimall.domain.dto.LoginForm;
import com.example.kojimall.domain.entity.Member;
import com.example.kojimall.domain.entity.OAuth;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public interface JpaMemberRepositoryCustom{

    Optional<Member> getLoginMember(LoginForm loginForm);

}
