package com.example.kojimall.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(schema = "kojimall")
public class Size {

    @Id
    @GeneratedValue
    private Long sizeId;

    @Column(name = "size_nm")
    private String sizeNm;

    public Size(String sizeNm) {
        this.sizeNm = sizeNm;
    }
}
