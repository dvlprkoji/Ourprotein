package com.example.kojimall.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(schema = "kojimall")
@IdClass(CodeKey.class)
public class Code implements Serializable {
    @Id
    private String cd;
    @Id
    @Column(name = "grp_cd")
    private String
            grpCd;
    @Column(name = "cd_nm")
    private String cdNm;
    @Column(name = "grp_cd_nm")
    private String grpCdNm;

}
