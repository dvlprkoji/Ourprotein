package com.example.kojimall.config;

import com.example.kojimall.domain.entity.Code;
import com.example.kojimall.service.CodeService;
import com.example.kojimall.service.ItemService;
import com.example.kojimall.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.RegEx;

@RequiredArgsConstructor
@Component
public class InitDb {
    private final MemberService memberService;
    private final CodeService codeService;
    private final ItemService itemService;

//    @PostConstruct
    public void init() {
        codeService.initCode();
        memberService.initMember();
        itemService.initTag();
        itemService.initFlavor();
        itemService.initSize();
    }
}
