package com.example.kojimall.repository.OAuth;

import com.example.kojimall.domain.entity.OAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OAuthRepository extends JpaRepository<OAuth, Long>, OAuthRepositoryCustom {
}
