package com.example.kojimall.domain.entity;

import com.example.kojimall.domain.entity.BaseTimeEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(schema = "kojimall")
public class Flavor extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "flv_id")
    private Long flvId;

    @Column(name = "flv_nm")
    private String flvNm;

    @Column(name = "use_yn", columnDefinition = "varchar default 'Y'")
    private String useYn;

    public Flavor(String flvNm) {
        this.flvNm = flvNm;
    }
}
