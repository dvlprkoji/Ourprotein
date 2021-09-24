package com.example.kojimall.service;

import com.example.kojimall.domain.*;
import com.example.kojimall.domain.dto.ItemDtl.ClothingItemDtl;
import com.example.kojimall.domain.dto.ItemDtl.ItemDtl;
import com.example.kojimall.domain.dto.ItemDtl.NutritionItemDtl;
import com.example.kojimall.domain.entity.*;
import com.example.kojimall.repository.Flavor.JpaFlavorRepository;
import com.example.kojimall.repository.Item.JpaItemRepository;
import com.example.kojimall.repository.ItemRepository;
import com.example.kojimall.repository.ItemStcRepository;
import com.example.kojimall.repository.Size.SizeRepository;
import com.example.kojimall.repository.Stock.ClothingStockRepository;
import com.example.kojimall.repository.Stock.NutritionStockRepository;
import com.example.kojimall.repository.Stock.StockRepository;
import com.example.kojimall.repository.Tag.JpaTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.example.kojimall.domain.DiscountRate.*;
import static java.lang.Math.round;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final JpaFlavorRepository flavorRepository;
    private final StockRepository stockRepository;
    private final ItemRepository itemRepository;
    private final ItemStcRepository itemStcRepository;
    private final JpaItemRepository jpaItemRepository;
    private final JpaTagRepository tagRepository;
    private final SizeRepository sizeRepository;
    private final ClothingStockRepository clothingStockRepository;
    private final NutritionStockRepository nutritionStockRepository;

    public void initSize() {
        sizeRepository.save(new Size("XXXL"));
        sizeRepository.save(new Size("XXL"));
        sizeRepository.save(new Size("XL"));
        sizeRepository.save(new Size("L"));
        sizeRepository.save(new Size("M"));
        sizeRepository.save(new Size("S"));
    }

    public void initFlavor() {
        flavorRepository.save(new Flavor("Chocolate Caramel"));
        flavorRepository.save(new Flavor("Matcha Latte"));
        flavorRepository.save(new Flavor("Golden Syrup"));
        flavorRepository.save(new Flavor("Strawberry Cream"));
        flavorRepository.save(new Flavor("Salted Caramel"));
    }

    public void initTag() {
        tagRepository.save(new Tag("WPC"));
        tagRepository.save(new Tag("WPI"));
        tagRepository.save(new Tag("WPH"));
        tagRepository.save(new Tag("Protein"));
        tagRepository.save(new Tag("Bottle"));
        tagRepository.save(new Tag("Strength"));
        tagRepository.save(new Tag("BCAA"));
        tagRepository.save(new Tag("Recovery"));
        tagRepository.save(new Tag("Pre-Workout"));
        tagRepository.save(new Tag("Booster"));
        tagRepository.save(new Tag("Caffeine"));
        tagRepository.save(new Tag("Nuts"));
        tagRepository.save(new Tag("Health"));
        tagRepository.save(new Tag("Snack"));
    }

    public List<Tag> getTags(String word) {
        if (word.equals("")) {
            return Collections.emptyList();
        }
        List<Tag> tags = itemRepository.getTags(word);
        if (tags.isEmpty()) {
            log.info("no such tags");
        }
        return tags;
    }

    public List<Tag> getTags(List<String> words) {
        List<Tag> tagList = new ArrayList<>();
        for (String word : words) {
            tagList.add(itemRepository.getTag(word));
        }
        return tagList;
    }

    public void saveItem(Item item) {
        jpaItemRepository.save(item);
    }

    public List<ItemTag> saveItemTagList(Item item, List<Tag> tagList) {
        List<ItemTag> itemTagList = new ArrayList<>();
        for (Tag tag : tagList) {
            ItemTag itemTag = new ItemTag();
            itemTag.setItem(item);
            itemTag.setTag(tag);
            itemRepository.saveItemTag(itemTag);
            itemTagList.add(itemTag);
        }
        return itemTagList;
    }



    public Long getItemCnt(String category) {
        if (category == null || category.equals("all")) {
            return itemRepository.getItemCnt();
        } else {
            return itemRepository.getItemCnt(category);
        }
    }

    public Item getItem(Long id) {
        return itemRepository.getItem(id);
    }

    public ItemDtl getItemDtl(Member member, Item item, List<Image> imgList, List<Tag> tagList, List<Stock> stockList) {
        ItemDtl itemDtl;
        if (item.getCategory().getCd().equals("C0001")) {
            itemDtl = new NutritionItemDtl();
        }
        else{
            itemDtl = new ClothingItemDtl();
        }
        itemDtl.setItemId(item.getItemId());
        itemDtl.setItemNm(item.getItemNm());
        itemDtl.setItemDsc(item.getItemDsc());
        itemDtl.setCategory(item.getCategory());
        itemDtl.setItemRat(item.getItemRat());
        List<String> imgPathList = new ArrayList<>();
        for (Image image : imgList) {
            imgPathList.add(image.getPath());
        }
        itemDtl.setImgPathList(imgPathList);
        itemDtl.setTagList(tagList);

        List<Stock> dscStockList = new ArrayList<>();
        for (Stock stock : stockList) {
            Stock dscStock = item.getCategory().getCd().equals("C0001") ? new DiscountNutritionStock() : new DiscountClothingStock();
            this.discount(dscStock, stock, member);
            dscStockList.add(dscStock);
        }
        itemDtl.setStockList(dscStockList);
        return itemDtl;
    }

    public void discount(Stock dscStock, Stock stock, Member member) {
        Long dscPrc = stock.getStcPrc();
        dscStock.setId(stock.getId());
        dscStock.setStcPrc(stock.getStcPrc());
        dscStock.setStcQty(stock.getStcQty());
        dscStock.setItem(stock.getItem());
        if (stock.getItem().getCategory().getCd().equals("C0001")) {
            ((DiscountNutritionStock)dscStock).setFlavor(((NutritionStock) stock).getFlavor());
            ((DiscountNutritionStock)dscStock).setStcAmt(((NutritionStock) stock).getStcAmt());
        }
        else {
            ((DiscountClothingStock)dscStock).setSize(((ClothingStock) stock).getSize());
        }
        Double dscRate = NORMAL;
        if (member == null) {
            if (stock.getItem().getCategory().getCd().equals("C0001")) {
                ((DiscountNutritionStock)dscStock).setItemDscPrc(dscStock.getStcPrc());
                ((DiscountNutritionStock)dscStock).setItemDscAmt(round(0.0));
            }
            else{
                ((DiscountClothingStock)dscStock).setItemDscPrc(dscStock.getStcPrc());
                ((DiscountClothingStock)dscStock).setItemDscAmt(round(0.0));
            }
            return;
        }
        if (member.getSubYn().equals("Y")){
            dscRate += SUBSCRIBE;
        }
        if (member.getMemberRole().getCdNm().equals("VIP")) {
            dscRate += VIP;
        }

        if (stock.getItem().getCategory().getCd().equals("C0001")) {
            ((DiscountNutritionStock)dscStock).setItemDscPrc(round((double) dscPrc * (1-dscRate)));
            ((DiscountNutritionStock)dscStock).setItemDscAmt(dscPrc - ((DiscountNutritionStock)dscStock).getItemDscPrc());
        }
        else{
            ((DiscountClothingStock)dscStock).setItemDscPrc(round((double) dscPrc * (1-dscRate)));
            ((DiscountClothingStock)dscStock).setItemDscAmt(dscPrc - ((DiscountClothingStock)dscStock).getItemDscPrc());
        }
        return;
    }

    public List<Flavor> getFlavorList() {
        return flavorRepository.findAll();
    }

    public Flavor getFlavor(Long id) {
        return flavorRepository.findById(id).get();
    }

    public void saveItemStc(Item item,
                            Flavor flavor,
                            Long itemStcAmt,
                            String itemStcUnit,
                            Long itemPrc) {
        ItemStc itemStc = new ItemStc();
        itemStc.setItem(item);
        itemStc.setItemStcAmt(itemStcAmt);
        itemStc.setItemStcUnit(itemStcUnit);
        itemStc.setItemPrc(itemPrc);
        itemStc.setFlavor(flavor);
        itemStcRepository.saveItemStc(itemStc);
    }

    public List<Flavor> getFlavors(List<String> flavorIdList) {
        ArrayList<Flavor> flavorList = new ArrayList<>();
        for (String id : flavorIdList) {
            Flavor flavor = flavorRepository.findById(Long.parseLong(id)).get();
            flavorList.add(flavor);
        }
        return flavorList;
    }

    public Long getMinPrc(Item item) {
        return stockRepository.getMinPrice(item);
    }

    public List<ItemStc> getItemStcList(Item item) {
        return itemStcRepository.getItemStcList(item);
    }


    public List<Size> getSizeList() {
        return sizeRepository.findAll();
    }

    public List<Size> getSizes(List<String> sizeList) {
        List<Long> longSizeList = new LinkedList<>();
        for (String id : sizeList) {
            longSizeList.add(Long.parseLong(id));
        }
        return sizeRepository.findAllById(longSizeList);
    }

    public void saveNutritionStc(Item item, Flavor flavor, long quantity, String amount, long itemPrc) {
        NutritionStock nutritionStock = new NutritionStock();
        nutritionStock.setFlavor(flavor);
        nutritionStock.setStcAmt(amount);
        nutritionStock.setItem(item);
        nutritionStock.setStcPrc(itemPrc);
        nutritionStock.setStcQty(quantity);

        nutritionStockRepository.save(nutritionStock);
    }

    public void saveClothingStc(Item item, Size size, long quantity, long itemPrc) {
        ClothingStock clothingStock = new ClothingStock();
        clothingStock.setSize(size);
        clothingStock.setItem(item);
        clothingStock.setStcPrc(itemPrc);
        clothingStock.setStcQty(quantity);

        clothingStockRepository.save(clothingStock);
    }

    public Page<Item> findByCategory(Code categoryCode, PageRequest pageRequest) {
        return jpaItemRepository.findByCategory(categoryCode, pageRequest);
    }

    public Page<Item> findAll(PageRequest pageRequest) {
        return jpaItemRepository.findAll(pageRequest);
    }

    public List<Stock> getStockList(Long id) {
        Item item = itemRepository.getItem(id);
        if (item.getCategory().getCd().equals("C0001")) {
            return nutritionStockRepository.findByItem(item);
        } else{
            return clothingStockRepository.findByItem(item);
        }
    }

    public void viewUp(Item item) {
        jpaItemRepository.viewUp(item);
    }
}
