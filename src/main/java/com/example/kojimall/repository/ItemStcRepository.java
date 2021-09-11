package com.example.kojimall.repository;

import com.example.kojimall.domain.Item;
import com.example.kojimall.domain.ItemStc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemStcRepository {

    private final EntityManager em;

    public void saveItemStc(ItemStc itemStc) {
        em.persist(itemStc);
    }

    public Long getMinPrc(Item item) {
        TypedQuery<Long> query = em.createQuery("select min(stc.itemPrc) from ItemStc stc where stc.item=:item", Long.class);
        query.setParameter("item", item);
        return query.getSingleResult();
    }

    public List<ItemStc> getItemStcList(Item item) {
        TypedQuery<ItemStc> query = em.createQuery("select stc from ItemStc stc where stc.item=:item", ItemStc.class);
        query.setParameter("item", item);
        return query.getResultList();
    }
}
