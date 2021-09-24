package com.example.kojimall.controller;

import com.example.kojimall.common.CommonUtils;
import com.example.kojimall.common.Pagination;
import com.example.kojimall.domain.dto.ItemDtl.ItemDtl;
import com.example.kojimall.domain.dto.ItemPreview;
import com.example.kojimall.domain.entity.*;
import com.example.kojimall.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final CommonUtils commonUtils;
    private final ItemService itemService;
    private final CodeService codeService;
    private final ImageService imageService;
    private final TagService tagService;
    private final MemberService memberService;

    @GetMapping("/item")
    public String list(@RequestParam(name = "page") Integer page,
                       @RequestParam(name = "size") Integer size,
                       @RequestParam(name = "category") String category,
                       @Nullable @SessionAttribute("loginMember") Member member,
                       Model model) {
        page -= 1;
        Page<ItemPreview> itemPreviewList;
        if (category.equals("all")) {
            itemPreviewList = itemService
                    .findAll(PageRequest.of(page, size, Sort.Direction.DESC, "regDt"))
                    .map(item -> toItemPreview(item));
        }
        else{
            // ItemList
            itemPreviewList = itemService
                    .findByCategory(codeService.getCategoryCode(category), PageRequest.of(page, size, Sort.Direction.DESC, "regDt"))
                    .map(item -> toItemPreview(item));
        }
        model.addAttribute("itemPreviewList", itemPreviewList);

        // Pagination
        Pagination pagination = new Pagination();
        pagination.pageInfo(itemPreviewList);

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
        if (member != null) {
            model.addAttribute("loginMember", memberService.toLoginMember(member, codeService.getEmailLoginCode()));

        }
        return "item";
    }

    private ItemPreview toItemPreview(Item item) {
        ItemPreview itemPreview = new ItemPreview();
        itemPreview.setItemId(item.getItemId());
        itemPreview.setItemNm(item.getItemNm());

        itemPreview.setItemPrc(itemService.getMinPrc(item));

        Image mainImage = imageService.getProductMainImage(item.getImgGrpId());
        itemPreview.setMainImagePath(mainImage.getPath());
        if (imageService.getImageCnt(item.getImgGrpId()) > 1) {
            Image hoverImage = imageService.getProductHoverImage(item.getImgGrpId());
            itemPreview.setHoverImagePath(hoverImage.getPath());
        }

        return itemPreview;
    }

    @GetMapping("/item/detail")
    public String list(@RequestParam(name = "id") Long id,
                       @Nullable @SessionAttribute(name="loginMember") Member member,
                       Model model) {
        Item item = itemService.getItem(id);
        itemService.viewUp(item);
        List<Image> imageList = imageService.getProductImageList(item.getImgGrpId());
        List<Tag> tagList = tagService.getTags(item);
        List<Stock> stockList = itemService.getStockList(id);
        ItemDtl itemDtl = itemService.getItemDtl(member, item, imageList, tagList, stockList);
        model.addAttribute("item", itemDtl);
        if (member != null) {
            model.addAttribute("loginMember", memberService.toLoginMember(member, codeService.getEmailLoginCode()));
        }
        return "itemdtl";
    }
    @GetMapping("/item/add")
    public String add(Model model, @SessionAttribute(name = "loginMember") Member member) {

        model.addAttribute("loginMember", memberService.toLoginMember(member, codeService.getEmailLoginCode()));
        List<Code> categoryList = codeService.getCategoryList();
        model.addAttribute("categoryList", categoryList);
        List<Flavor> flavorList = itemService.getFlavorList();
        List<Size> sizeList = itemService.getSizeList();
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("flavorList", flavorList);

        return "add";
    }

    @PostMapping("/item/add")
    public String add(MultipartHttpServletRequest request) throws IOException {
        commonUtils.printParams(request);

        ArrayList<String> arrayList = new ArrayList<>();


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
        item.setCategory(codeService.getCategoryCode(parameterMap.get("category")[0]));
        item.setImgGrpId(imgGrpId);
        String[] selectTags = parameterMap.get("selectTags")[0].split(",");
        List<Tag> tagList = itemService.getTags(Arrays.asList(selectTags));
        List<ItemTag> itemTagList = itemService.saveItemTagList(item, tagList);
        item.setItemTagList(itemTagList);
        itemService.saveItem(item);


        if (request.getParameter("category").equals("C0001")) {
            String[] selectFlavors = parameterMap.get("selectFlavor")[0].split(",");
            List<Flavor> flavorList = itemService.getFlavors(Arrays.asList(selectFlavors));

            Integer i = 0;
            for (Flavor flavor : flavorList) {
                itemService.saveNutritionStc(
                        item,
                        flavor,
                        Long.parseLong(parameterMap.get("Quantity")[i]),
                        parameterMap.get("Amount")[i],
                        Long.parseLong(parameterMap.get("itemPrc")[i]));
                i += 1;
            }
        }

        else if (request.getParameter("category").equals("C0002")) {
            String[] selectSizes = parameterMap.get("selectSize")[0].split(",");
            List<Size> sizeList = itemService.getSizes(Arrays.asList(selectSizes));

            Integer i = 0;
            for (Size size : sizeList) {
                itemService.saveClothingStc(item,
                        size,
                        Long.parseLong(parameterMap.get("Quantity")[i]),
                        Long.parseLong(parameterMap.get("itemPrc")[i]));
                i += 1;
            }
        }

        return "redirect:/";
    }

    @GetMapping("/search/tag")
    @ResponseBody
    public List<Tag> searchTag(@RequestParam(name = "searchNm") String word) {
        List<Tag> tags = itemService.getTags(word);
        return tags;
    }


}
