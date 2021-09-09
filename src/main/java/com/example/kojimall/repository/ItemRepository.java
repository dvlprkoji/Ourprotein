package com.example.kojimall.repository;

import com.example.kojimall.domain.*;
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

    public List<Item> getItemList(Integer page, Integer range, Code code) {
        TypedQuery<Item> query = em.createQuery("select i from Item i where i.category=:category", Item.class);
        query.setParameter("category", code.getCd());
        List<Item> resultList = query.getResultList();
        return resultList;
    }

    public List<Item> getItemList(Tag tag) {
        TypedQuery<Item> query = em.createQuery("select it.item from ItemTag it where it.tag=:tag", Item.class);
        query.setParameter("tag", tag);
        List<Item> resultList = query.getResultList();
        return resultList;
    }

    public List<Item> getItemList(Integer startList, Integer endList) {
        TypedQuery<Item> query = em.createQuery("select i from Item i order by i.regDt", Item.class);
        query.setFirstResult(startList);
        query.setMaxResults(endList);
        List<Item> resultList = query.getResultList();
        return resultList;
    }

    public Long getItemCnt(String category) {
        TypedQuery<Long> query = em.createQuery("select count(i) from Item i where i.category.cd=:cd", Long.class);
        query.setParameter("cd", category);
        return query.getSingleResult();
    }

    public Long getItemCnt() {
        TypedQuery<Long> query = em.createQuery("select count(i) from Item i", Long.class);
        return query.getSingleResult();
    }


    public Item getItem(Long id) {
        TypedQuery<Item> query = em.createQuery("select i from Item i where i.itemId=:id", Item.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

}
