package com.example.kojimall.repository;

import com.example.kojimall.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    private final EntityManager em;

    public void saveImg(Image image) {
        em.persist(image);
    }
}
