package com.example.kojimall.domain.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicInsert
@Table(schema = "kojimall")
public class Item extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_nm")
    private String itemNm;

    @Column(name = "item_dsc")
    private String itemDsc;

    @Column(name = "img_grp_id")
    private String imgGrpId;

    @Column(name = "item_rat", nullable = false, columnDefinition = "numeric(2,1) default '5.0'")
    private Double itemRat = 5.0d;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "cd", referencedColumnName = "cd"),
            @JoinColumn(name = "grp_cd", referencedColumnName = "grp_cd")
    })
    private Code category;

    @JsonIgnore
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Stock> stockList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ItemTag> itemTagList = new ArrayList<>();

    @Column(name="item_views")
    private Long itemViews = 0l;

    public Long getItemViews() {
        return itemViews;
    }

    public void setItemViews(Long itemViews) {
        this.itemViews = itemViews;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemNm() {
        return itemNm;
    }

    public void setItemNm(String itemNm) {
        this.itemNm = itemNm;
    }

    public String getItemDsc() {
        return itemDsc;
    }

    public void setItemDsc(String itemDsc) {
        this.itemDsc = itemDsc;
    }

    public String getImgGrpId() {
        return imgGrpId;
    }

    public void setImgGrpId(String imgGrpId) {
        this.imgGrpId = imgGrpId;
    }

    public Double getItemRat() {
        return itemRat;
    }

    public void setItemRat(Double itemRat) {
        this.itemRat = itemRat;
    }

    public Code getCategory() {
        return category;
    }

    public void setCategory(Code category) {
        this.category = category;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public List<ItemTag> getItemTagList() {
        return itemTagList;
    }

    public void setItemTagList(List<ItemTag> itemTagList) {
        this.itemTagList = itemTagList;
    }
}
