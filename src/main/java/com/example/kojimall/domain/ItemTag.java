package com.example.kojimall.domain;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "kojimall")
public class ItemTag {

    @Id @GeneratedValue
    @Column(name="itemtag_id")
    public Long itemTagId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    public Item item;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id")
    public Tag tag;
}
