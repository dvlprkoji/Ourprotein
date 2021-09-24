package com.example.kojimall.repository.Stock;

import com.example.kojimall.domain.entity.Item;

public interface StockRepositoryCustom {

    Long getMinPrice(Item item);
}
