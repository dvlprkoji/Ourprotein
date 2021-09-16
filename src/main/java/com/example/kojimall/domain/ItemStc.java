package com.example.kojimall.domain;

import com.example.kojimall.domain.entity.Item;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(schema = "kojimall")
public class ItemStc{

    @Id
    @GeneratedValue
    @Column(name = "item_stc_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flv_id")
    private Flavor flavor;

    @Column(name = "item_stc_unit")
    private String itemStcUnit;

    @Column(name = "item_stc_amt")
    private Long itemStcAmt;

    @Column(name = "item_stc_prc")
    private Long itemPrc;
}
