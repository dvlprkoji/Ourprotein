package com.example.kojimall.repository.Stock;

import com.example.kojimall.domain.entity.Item;
import com.example.kojimall.domain.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long>, StockRepositoryCustom {

    List<Stock> findByItem(Item item);
}
