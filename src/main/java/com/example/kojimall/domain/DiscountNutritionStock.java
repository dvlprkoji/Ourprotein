package com.example.kojimall.domain;

import com.example.kojimall.domain.entity.NutritionStock;
import lombok.Data;

@Data
public class DiscountNutritionStock extends NutritionStock {

    private Long itemDscPrc;
    private Long itemDscAmt;
}
