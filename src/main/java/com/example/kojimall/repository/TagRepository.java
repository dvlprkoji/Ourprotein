package com.example.kojimall.repository;

import com.example.kojimall.domain.entity.Item;
import com.example.kojimall.domain.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepository {

    private final EntityManager em;

    public List<Tag> getTags(Item item) {
        TypedQuery<Tag> query = em.createQuery("select it.tag from ItemTag it where it.item=:item", Tag.class);
        query.setParameter("item", item);
        return query.getResultList();
    }

}
