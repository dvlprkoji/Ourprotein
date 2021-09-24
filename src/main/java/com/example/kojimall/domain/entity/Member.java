package com.example.kojimall.domain.entity;

import com.example.kojimall.domain.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "kojimall")
@NoArgsConstructor
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "mem_id")
    private Long id;
    @Column(name = "mem_nm")
    private String name;
    @Column(name = "mem_eml")
    private String email;
    @Column(name = "mem_pw")
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "cd", referencedColumnName = "cd"),
            @JoinColumn(name = "grp_cd", referencedColumnName = "grp_cd")
    })
    private Code memberRole;

    @Embedded
    private Address address;

    @Column(name = "sub_yn")
    private String subYn;

    public Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}
