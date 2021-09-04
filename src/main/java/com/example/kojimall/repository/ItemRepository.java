package com.example.kojimall.repository;

import com.example.kojimall.domain.Image;
import com.example.kojimall.domain.Item;
import com.example.kojimall.domain.ItemTag;
import com.example.kojimall.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ItemRepository {

    private final EntityManager em;

    public List<Tag> getTags(String word) {
        TypedQuery<Tag> query = em.createQuery("select t from Tag t where t.tagNm like CONCAT('%',:word,'%')", Tag.class);
        query.setParameter("word", word);
        List<Tag> resultList = query.getResultList();
        return resultList;
    }

    public Tag getTag(String word) {
        TypedQuery<Tag> query = em.createQuery("select t from Tag t where t.tagNm=:word", Tag.class);
        query.setParameter("word", word);
        Tag singleResult = query.getSingleResult();
        return singleResult;
    }

    public void saveItemTag(ItemTag itemTag) {
        em.persist(itemTag);
    }
}
