package com.example.kojimall.repository.Kakao;

import com.example.kojimall.domain.entity.Kakao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoRepository extends JpaRepository<Kakao, Integer>, KakaoRepositoryCustom {
}
