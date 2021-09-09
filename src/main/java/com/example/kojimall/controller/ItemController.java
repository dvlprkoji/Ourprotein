package com.example.kojimall.controller;

import com.example.kojimall.common.Pagination;
import com.example.kojimall.domain.*;
import com.example.kojimall.service.CodeService;
import com.example.kojimall.service.ImageService;
import com.example.kojimall.service.ItemService;
import com.example.kojimall.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.constraints.Null;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.example.kojimall.domain.CodeVal.PRODUCT_MAIN;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CodeService codeService;
    private final ImageService imageService;
    private final TagService tagService;

    @GetMapping("/item")
    public String list(@Nullable @RequestParam(name = "page") Integer page,
                       @Nullable @RequestParam(name = "range") Integer range,
                       @Nullable @RequestParam(name = "category") String category,
                       @Nullable @SessionAttribute("loginMember") Member member,
                       Model model) {

        // Pagination
        Pagination pagination = new Pagination();
        Integer itemCnt = itemService.getItemCnt(category).intValue();
        if (page == null) {
            pagination.pageInfo(1, 1, itemCnt);
        } else {
            pagination.pageInfo(page, range, itemCnt);
        }
        model.addAttribute("pagination", pagination);

        //  Category List
        List<Code> categoryList = codeService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        // Category
        if (category == null) {
            model.addAttribute("category", "all");
        } else {
            model.addAttribute("category", category);
        }

        // ItemList
        List<Item> itemList = itemService.getItemList(pagination.getStartList(), pagination.getEndList());
        model.addAttribute("itemList", itemList);

        List<ItemPreview> itemPreviewList = new ArrayList<>();
        for (Item item : itemList) {
            ItemPreview itemPreview = new ItemPreview();
            itemPreview.setItemId(item.getItemId());
            itemPreview.setItemNm(item.getItemNm());
            itemPreview.setItemPrc(item.getItemPrc());

            Image mainImage = imageService.getProductMainImage(item.getImgGrpId());
            itemPreview.setMainImagePath(mainImage.getPath());
            if (imageService.getImageCnt(item.getImgGrpId()) > 1) {
                Image hoverImage = imageService.getProductHoverImage(item.getImgGrpId());
                itemPreview.setHoverImagePath(hoverImage.getPath());
            }

            itemPreviewList.add(itemPreview);
        }
        model.addAttribute("itemPreviewList", itemPreviewList);

        model.addAttribute("loginMember", member);
        return "item";
    }

    @GetMapping("/item/detail")
    public String list(@RequestParam(name = "id") Long id,
                       @Nullable @SessionAttribute(name="loginMember") Member member,
                       Model model) {
        Item item = itemService.getItem(id);
        List<Image> imageList = imageService.getProductImageList(item.getImgGrpId());

        List<Tag> tagList = tagService.getTags(item);
        ItemDtl itemDtl = itemService.getItemDtl(item, imageList, tagList);
        itemService.setDscPrc(itemDtl, member);
        model.addAttribute("item", itemDtl);
        if (member != null) {
            model.addAttribute("loginMember", member);
        }
        return "itemdtl";
    }
    @GetMapping("/item/add")
    public String add(Model model, @SessionAttribute(name = "loginMember") Member member) {

        model.addAttribute("loginMember", member);
        List<Code> categoryList = codeService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        return "add";
    }

    @PostMapping("/item/add")
    public String add(MultipartHttpServletRequest request) throws IOException {

        Map<String, MultipartFile> fileMap = request.getFileMap();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String imgGrpId = UUID.randomUUID().toString();
        Integer order = 0;
        for (String s : fileMap.keySet()) {
            MultipartFile multipartFile = fileMap.get(s);
            String uploadUrl = imageService.upload(multipartFile, "kojimall");
            Code imageCode = new Code();
            switch (order++) {
                case 0:
                    imageCode = codeService.getMainImageCode();
                    break;
                case 1:
                    imageCode = codeService.getHoverImageCode();
                    break;
                default:
                    imageCode = codeService.getNormalImageCode();
                    break;
            }
            imageService.saveImg(multipartFile, uploadUrl, imgGrpId, imageCode);
        }
        Item item = new Item();
        item.setItemNm(parameterMap.get("itemNm")[0]);
        item.setItemDsc(parameterMap.get("itemDsc")[0]);
        item.setItemPrc(Long.parseLong(parameterMap.get("itemPrc")[0]));
        item.setItemStc(Long.parseLong(parameterMap.get("itemAmt")[0]));
        item.setCategory(codeService.getCategoryCode(parameterMap.get("category")[0]));
        item.setImgGrpId(imgGrpId);
        String[] selectTags = parameterMap.get("selectTags")[0].split(",");
        List<Tag> tagList = itemService.getTags(Arrays.asList(selectTags));
        List<ItemTag> itemTagList = itemService.saveItemTagList(item, tagList);
        item.setItemTagList(itemTagList);
        itemService.saveItem(item);

        return "redirect:/";
    }

    @GetMapping("/search/tag")
    @ResponseBody
    public List<Tag> searchTag(@RequestParam(name = "searchNm") String word) {
        List<Tag> tags = itemService.getTags(word);
        return tags;
    }

}
