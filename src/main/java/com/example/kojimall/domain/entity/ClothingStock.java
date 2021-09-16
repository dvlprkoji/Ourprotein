package com.example.kojimall.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "clothing_stc")
@Table(schema = "kojimall")
public class ClothingStock extends Stock {

    @Column(name = "stc_sz")
    private String stcSz;
}
