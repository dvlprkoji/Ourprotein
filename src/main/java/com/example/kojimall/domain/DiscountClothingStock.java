package com.example.kojimall.domain;

import com.example.kojimall.domain.entity.ClothingStock;
import lombok.Data;

@Data
public class DiscountClothingStock extends ClothingStock {

    private Long itemDscPrc;
    private Long itemDscAmt;
}
