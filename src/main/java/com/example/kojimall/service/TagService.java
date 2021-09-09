package com.example.kojimall.service;

import com.example.kojimall.domain.Item;
import com.example.kojimall.domain.Tag;
import com.example.kojimall.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> getTags(Item item) {
        return tagRepository.getTags(item);
    }

}