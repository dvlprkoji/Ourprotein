package com.example.kojimall.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(schema = "kojimall")
public class Kakao {
    @Id
    @Column(name = "kakao_id")
    private Integer kakaoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oauth_id")
    private OAuth oAuth;

    public Kakao(Integer id, OAuth oAuth) {
        this.kakaoId = id;
        this.oAuth = oAuth;
    }
}

