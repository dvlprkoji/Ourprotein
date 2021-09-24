package com.example.kojimall.repository.Item;

import com.example.kojimall.domain.entity.Item;
import com.example.kojimall.domain.entity.QItem;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.example.kojimall.domain.entity.QItem.*;
import static com.example.kojimall.domain.entity.QItem.item;

public class JpaItemRepositoryImpl implements JpaItemRepositoryCustom {

    private final JPAQueryFactory qf;

    public JpaItemRepositoryImpl(EntityManager em) {
        qf = new JPAQueryFactory(em);
    }

    @Override
    public void viewUp(Item viewItem) {
        qf
                .update(item)
                .where(item.eq(viewItem))
                .set(item.itemViews, viewItem.getItemViews() + 1l)
                .execute();

    }
}
