package com.example.kojimall.domain.entity;

import javax.persistence.*;

@Entity
@Table(schema = "kojimall")
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
public class Stock {

    @Id
    @GeneratedValue
    @Column(name = "stc_id")
    private Long id;

    @Column(name = "stc_qty")
    private Long stcQty;

    @Column(name = "stc_prc")
    private Long stcPrc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

}
