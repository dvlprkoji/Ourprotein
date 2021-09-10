package com.example.kojimall.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(schema = "kojimall")
public class Flavor {

    @Id
    @GeneratedValue
    @Column(name = "flv_id")
    private Long flvId;

    @Column(name = "flv_nm")
    private String flvNm;

    @Column(name = "use_yn")
    private String useYn;

    @Column(name = "reg_dt")
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column(name = "mod_dt")
    @CreationTimestamp
    private LocalDateTime modDt;
}
