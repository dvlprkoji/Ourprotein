package com.example.kojimall.domain;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(schema = "kojimall")
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_nm")
    private String itemNm;

    @Column(name = "item_prc")
    private Long itemPrc;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "cd", referencedColumnName = "cd"),
            @JoinColumn(name = "grp_cd", referencedColumnName = "grp_cd")
    })
    private Code category;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id")
    private Tag tagId;

    @Column(name = "reg_dt")
    private LocalDateTime regDt;

    @Column(name = "mod_dt")
    private LocalDateTime modDt;

}
