package com.example.kojimall.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "kojimall")
public class Tag {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "tag_nm")
    private String tagNm;

    @Column(name = "reg_dt")
    private String regDt;

    @Column(name = "mod_dt")
    private String modDt;
}
