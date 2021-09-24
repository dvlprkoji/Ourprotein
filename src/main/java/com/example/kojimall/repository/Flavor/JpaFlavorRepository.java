package com.example.kojimall.repository.Flavor;

import com.example.kojimall.domain.entity.Flavor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFlavorRepository extends JpaRepository<Flavor, Long>, JpaFlavorRepositoryCustom {
}
