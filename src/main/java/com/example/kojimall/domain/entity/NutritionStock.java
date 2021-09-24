package com.example.kojimall.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "nutrition_stc")
@Table(schema = "kojimall")
@Data
public class NutritionStock extends Stock {

    @ManyToOne
    @JoinColumn(name = "flv_id")
    private Flavor flavor;

    @Column(name = "stc_amt")
    private String stcAmt;

    public Flavor getFlavor() {
        return flavor;
    }

    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    public String getStcAmt() {
        return stcAmt;
    }

    public void setStcAmt(String stcAmt) {
        this.stcAmt = stcAmt;
    }
}
