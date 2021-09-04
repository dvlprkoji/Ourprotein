package com.example.kojimall.controller;

import com.example.kojimall.domain.*;
import com.example.kojimall.service.CodeService;
import com.example.kojimall.service.ImageService;
import com.example.kojimall.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CodeService codeService;
    private final ImageService imageService;

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
    public void add(MultipartHttpServletRequest request) throws IOException {
        Map<String, MultipartFile> fileMap = request.getFileMap();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String imgGrpId = UUID.randomUUID().toString();
        for (String s : fileMap.keySet()) {
            MultipartFile multipartFile = fileMap.get(s);
            String uploadUrl = imageService.upload(multipartFile, "kojimall");
            Code productCode = codeService.getProductCode();
            imageService.saveImg(multipartFile, uploadUrl, imgGrpId, productCode);
        }
        Item item = new Item();
        item.setItemNm(parameterMap.get("itemNm")[0]);
        item.setItemDsc(parameterMap.get("itemDsc")[0]);
        item.setItemPrc(Long.parseLong(parameterMap.get("itemPrc")[0]));
        item.setItemStc(Long.parseLong(parameterMap.get("itemAmt")[0]));
        item.setCategory(codeService.getCategoryCode(parameterMap.get("category")[0]));
        String[] selectTags = parameterMap.get("selectTags")[0].split(",");
        List<Tag> tagList = itemService.getTags(Arrays.asList(selectTags));
        List<ItemTag> itemTagList = itemService.saveItemTagList(item, tagList);
        item.setItemTagList(itemTagList);
        itemService.saveItem(item);
    }

    @GetMapping("/search/tag")
    @ResponseBody
    public List<Tag> searchTag(@RequestParam(name = "searchNm") String word) {

        List<Tag> tags = itemService.getTags(word);
        return tags;
    }

}
