package com.example.kojimall.domain.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ItemPreview {

    private Long itemId;

    private String itemNm;

    private Long itemPrc;

    private String mainImagePath;

    private String hoverImagePath;
}
