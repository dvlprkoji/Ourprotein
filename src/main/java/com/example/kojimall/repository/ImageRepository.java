package com.example.kojimall.repository;

import com.example.kojimall.domain.Code;
import com.example.kojimall.domain.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    private final EntityManager em;

    public void saveImg(Image image) {
        em.persist(image);
    }

    public Image getImage(String imgGrpId, Code cd) {
        TypedQuery<Image> query = em.createQuery("select i from Image i where i.imgGrpId=:imgGrpId and i.imgCd=:cd", Image.class);
        query.setParameter("imgGrpId", imgGrpId);
        query.setParameter("cd", cd);
        return query.getSingleResult();
    }

    public Long getImageCnt(String imgGrpId) {
        TypedQuery<Long> query = em.createQuery("select count(i) from Image i where i.imgGrpId=:imgGrpId", Long.class);
        query.setParameter("imgGrpId", imgGrpId);
        return query.getSingleResult();
    }

    public List<Image> getImageList(String imgGrpId) {
        TypedQuery<Image> query = em.createQuery("select i from Image i where i.imgGrpId=:imgGrpId", Image.class);
        query.setParameter("imgGrpId", imgGrpId);
        return query.getResultList();
    }
}
