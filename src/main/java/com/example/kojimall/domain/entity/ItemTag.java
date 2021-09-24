package com.example.kojimall.domain.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(schema = "kojimall")
public class ItemTag {

    @Id @GeneratedValue
    @Column(name="itemtag_id")
    public Long itemTagId;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    public Item item;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id")
    public Tag tag;

    @Column(name = "reg_dt")
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column(name = "mod_dt")
    @CreationTimestamp
    private LocalDateTime modDt;
}
