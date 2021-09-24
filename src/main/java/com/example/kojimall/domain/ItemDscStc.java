package com.example.kojimall.domain;

import com.example.kojimall.domain.entity.Flavor;
import lombok.Data;


@Data
public class ItemDscStc {

    private Flavor flavor;

    private String itemStcUnit;

    private Long itemStcAmt;

    private Long itemPrc;

    private Long itemDscPrc;

    private Long itemDscAmt;
}
