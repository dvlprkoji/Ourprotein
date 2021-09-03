package com.example.kojimall.controller;

import com.example.kojimall.domain.Code;
import com.example.kojimall.domain.Member;
import com.example.kojimall.domain.Tag;
import com.example.kojimall.service.CodeService;
import com.example.kojimall.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CodeService codeService;

    @GetMapping("/item")
    public String list() {
        return "item";
    }

    @GetMapping("/item/add")
    public String add(Model model, @SessionAttribute(name = "loginMember") Member member) {
        model.addAttribute("loginMember", member);
        List<Code> categoryList = codeService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "add";
    }

    @PostMapping("/item/add")
    public void add(MultipartHttpServletRequest request) {
        Map<String, MultipartFile> fileMap = request.getFileMap();
        for (String s : fileMap.keySet()) {
            System.out.println("fileMap.get(s) = " + fileMap.get(s));
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String s : parameterMap.keySet()) {
            System.out.println("s = " + s);
            String[] strings = parameterMap.get(s);
            for (String string : strings) {
                System.out.println(string);
            }
        }
    }

    @GetMapping("/search/tag")
    @ResponseBody
    public List<Tag> searchTag(@RequestParam(name = "searchNm") String word) {

        List<Tag> tags = itemService.getTags(word);
        return tags;
    }

}
