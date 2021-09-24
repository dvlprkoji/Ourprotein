package com.example.kojimall.repository.Item;

import com.example.kojimall.domain.entity.Code;
import com.example.kojimall.domain.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface JpaItemRepository extends JpaRepository<Item, Long>, JpaItemRepositoryCustom {

    Page<Item> findByCategory(Code category, Pageable pageable);
}
