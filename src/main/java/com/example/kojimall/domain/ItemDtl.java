package com.example.kojimall.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemDtl {

    private Long itemId;

    private String itemNm;

    private Long itemPrc;

    private Long itemDscPrc;

    private Long itemDscAmt;

    private Long itemStc;

    private Double itemRat;

    private String itemDsc;

    private Code category;

    private List<String> imgPathList;

    private List<Tag> tagList;
}
