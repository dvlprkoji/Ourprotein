package com.example.kojimall.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeKey implements Serializable {
    private String cd;
    private String grpCd;
}
