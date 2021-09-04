package com.example.kojimall.service;

import com.example.kojimall.domain.*;
import com.example.kojimall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;

import static com.example.kojimall.domain.CodeVal.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

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

    public void saveItem(Item item){

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
}
