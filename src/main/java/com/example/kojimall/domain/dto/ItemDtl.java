package com.example.kojimall.domain.dto;

import com.example.kojimall.domain.ItemDscStc;
import com.example.kojimall.domain.entity.Code;
import com.example.kojimall.domain.entity.Tag;
import lombok.Data;

import java.util.List;

@Data
public class ItemDtl {

    private Long itemId;

    private String itemNm;

    private Double itemRat;

    private String itemDsc;

    private Code category;

    private List<String> imgPathList;

    private List<Tag> tagList;

    private List<ItemDscStc> itemDscStcList;
}
