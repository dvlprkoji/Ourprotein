package com.example.kojimall.domain.entity;

import com.example.kojimall.domain.entity.ItemTag;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(schema = "kojimall")
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "tag_nm")
    private String tagNm;

    @OneToMany(mappedBy = "tag")
    private List<ItemTag> itemTagList;

    public Tag(String tagNm) {
        this.tagNm = tagNm;
    }

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

}
