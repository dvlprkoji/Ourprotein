package com.example.kojimall.repository;

import com.example.kojimall.domain.ItemStc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ItemStcRepository {

    private final EntityManager em;

    public void saveItemStc(ItemStc itemStc) {
        em.persist(itemStc);
    }

}
