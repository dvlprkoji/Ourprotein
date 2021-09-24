package com.example.kojimall.repository.Tag;

import com.example.kojimall.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTagRepository extends JpaRepository<Tag, Long>, JpaTagRepositoryCustom {
}
