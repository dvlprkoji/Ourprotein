package com.example.kojimall.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "nutrition_stc")
@Table(schema = "kojimall")
public class NutritionStock extends Stock {

    @Column(name = "flv_nm")
    private String flvNm;

    @Column(name = "stc_amt")
    private String stcAmt;
}
