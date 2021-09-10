package com.example.kojimall.repository;

import com.example.kojimall.domain.Flavor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FlavorRepository {

    public  final EntityManager em;

    public List<Flavor> getFlavorList() {
        TypedQuery<Flavor> query = em.createQuery("select f from Flavor f", Flavor.class);
        return query.getResultList();
    }

    public Flavor getFlavor(Long id) {
        TypedQuery<Flavor> query = em.createQuery("select f from Flavor f where f.flvId=:flvId", Flavor.class);
        query.setParameter("flvId", id);
        return query.getSingleResult();
    }

}
