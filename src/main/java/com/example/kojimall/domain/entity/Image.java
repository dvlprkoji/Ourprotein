package com.example.kojimall.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "kojimall")
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "img_id")
    private Long imgId;

    @Column(name = "img_grp_id")
    private String imgGrpId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "cd", referencedColumnName = "cd"),
            @JoinColumn(name = "grp_cd", referencedColumnName = "grp_cd")
    })
    private Code imgCd;

    @Column(name="path")
    private String path;

    @Column(name = "fe_nm")
    private String feNm;

    @Column(name = "fe_sz")
    private Long feSz;

    @Column(name = "use_yn")
    private String useYn;
}
