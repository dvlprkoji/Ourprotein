package com.example.kojimall.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "clothing_stc")
@Table(schema = "kojimall")
@Data
public class ClothingStock extends Stock {

    @ManyToOne
    @JoinColumn(name = "size_nm")
    private Size size;

}
