package com.example.kojimall.repository.Member;

import com.example.kojimall.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;



public interface JpaMemberRepository extends JpaRepository<Member, Long>, JpaMemberRepositoryCustom{

}
