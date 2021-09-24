package com.example.kojimall.repository.Size;

import com.example.kojimall.domain.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Long>, SizeRepositoryCustom {
}
