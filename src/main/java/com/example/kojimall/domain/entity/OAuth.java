package com.example.kojimall.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(schema = "kojimall")
@NoArgsConstructor
public class OAuth {

    @Id
    @GeneratedValue
    @Column(name = "oauth_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "oAuth")
    private Kakao kakao;


    public OAuth(Member member) {
        this.member = member;
    }
}
