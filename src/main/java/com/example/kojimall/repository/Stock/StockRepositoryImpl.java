package com.example.kojimall.repository.Stock;

import com.example.kojimall.domain.entity.Item;
import com.example.kojimall.domain.entity.QStock;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

import static com.example.kojimall.domain.entity.QStock.*;


public class StockRepositoryImpl implements StockRepositoryCustom{

    private final JPAQueryFactory qf;

    public StockRepositoryImpl(EntityManager em) {
        qf = new JPAQueryFactory(em);
    }

    @Override
    public Long getMinPrice(Item item) {
        return qf
                .select(stock.stcPrc.min())
                .from(stock)
                .where(stock.item.eq(item))
                .fetchOne();
    }
}
