package com.example.kojimall.service;

import com.example.kojimall.domain.Tag;
import com.example.kojimall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
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

}
