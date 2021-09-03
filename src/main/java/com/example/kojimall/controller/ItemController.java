package com.example.kojimall.controller;

import com.example.kojimall.domain.Code;
import com.example.kojimall.domain.Member;
import com.example.kojimall.service.CodeService;
import com.example.kojimall.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CodeService codeService;

    @GetMapping("/item")
    public String list() {
        return "item";
    }

    @GetMapping("/add")
    public String add(Model model, @SessionAttribute(name = "loginMember") Member member) {
        model.addAttribute("loginMember", member);
        List<Code> categoryList = codeService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "add";
    }
}
