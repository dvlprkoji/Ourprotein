package com.example.kojimall.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(schema = "kojimall")
@IdClass(CodeKey.class)
@NoArgsConstructor
public class Code implements Serializable {
    @Id
    private String cd;
    @Id
    @Column(name = "grp_cd")
    private String grpCd;
    @Column(name = "cd_nm")
    private String cdNm;
    @Column(name = "grp_cd_nm")
    private String grpCdNm;


    public Code(String cd, String grpCd, String cdNm, String grpCdNm) {
        this.cd = cd;
        this.grpCd = grpCd;
        this.cdNm = cdNm;
        this.grpCdNm = grpCdNm;
    }

}
