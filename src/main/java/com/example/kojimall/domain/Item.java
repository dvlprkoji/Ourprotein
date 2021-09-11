package com.example.kojimall.domain;


import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@DynamicInsert
@Table(schema = "kojimall")
public class Item {
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

    @Column(name = "item_rat", nullable = false, columnDefinition = "numeric(2,1) default '1.0'")
    private Double itemRat;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "cd", referencedColumnName = "cd"),
            @JoinColumn(name = "grp_cd", referencedColumnName = "grp_cd")
    })
    private Code category;


    @OneToMany(mappedBy = "item")
    private List<ItemTag> itemTagList;

    @Column(name = "reg_dt")
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column(name = "mod_dt")
    @CreationTimestamp
    private LocalDateTime modDt;



}
