package com.example.kojimall.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "kojimall")
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
public class Stock {

    @Id
    @GeneratedValue
    @Column(name = "stc_id")
    protected Long id;

    @Column(name = "stc_qty")
    protected Long stcQty;

    @Column(name = "stc_prc")
    protected Long stcPrc;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    protected Item item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStcQty() {
        return stcQty;
    }

    public void setStcQty(Long stcQty) {
        this.stcQty = stcQty;
    }

    public Long getStcPrc() {
        return stcPrc;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setStcPrc(Long stcPrc) {
        this.stcPrc = stcPrc;
    }
}
