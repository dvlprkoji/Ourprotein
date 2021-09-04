package com.example.kojimall.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "tag")
    private List<ItemTag> itemTagList;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagNm() {
        return tagNm;
    }

    public void setTagNm(String tagNm) {
        this.tagNm = tagNm;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getModDt() {
        return modDt;
    }

    public void setModDt(String modDt) {
        this.modDt = modDt;
    }
}
