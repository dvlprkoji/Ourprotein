package com.example.kojimall.repository;

import com.example.kojimall.domain.Code;
import com.example.kojimall.domain.CodeKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CodeRepository {
    private final EntityManager em;

    public List<Code> getCodes(String grpCd) {
        TypedQuery<Code> query = em.createQuery("select c from Code c where c.grpCd=:grpCd", Code.class);
        query.setParameter("grpCd", grpCd);
        List<Code> resultList = query.getResultList();
        return resultList;
    }
}
