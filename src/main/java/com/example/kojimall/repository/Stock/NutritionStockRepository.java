package com.example.kojimall.repository.Stock;

import com.example.kojimall.domain.entity.Item;
import com.example.kojimall.domain.entity.NutritionStock;
import com.example.kojimall.domain.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NutritionStockRepository extends JpaRepository<NutritionStock, Long> {
    List<Stock> findByItem(Item item);
}
