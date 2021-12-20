package com.spbstu.edu.advertisement.repository.impl;

import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.entity.SubCategory;
import com.spbstu.edu.advertisement.repository.PageableAdRepository;
import com.spbstu.edu.advertisement.vo.PageableContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PageableAdRepositoryImpl implements PageableAdRepository {
    private static final Integer COUNT = 10;
    private static final String ID_PATH = "id";

    private final EntityManager entityManager;

    @Override
    public List<Ad> findAdsByFilters(PageableContext pageableContext) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Ad> criteriaQuery = criteriaBuilder.createQuery(Ad.class);
        Root<Ad> ad = criteriaQuery.from(Ad.class);
        CriteriaQuery<Ad> select = criteriaQuery
                .select(ad)
                .where(getPredicate(pageableContext, criteriaQuery, criteriaBuilder, ad))
                .orderBy(criteriaBuilder.desc(ad.get(Ad.ID_PATH)));

        TypedQuery<Ad> typedQuery = entityManager.createQuery(select);

        typedQuery.setFirstResult(COUNT * (pageableContext.getPage() - 1));
        typedQuery.setMaxResults(COUNT);

        return typedQuery.getResultList();
    }

    @Override
    public Long countAdsByFilter(PageableContext pageableContext) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Ad> ad = criteriaQuery.from(Ad.class);
        CriteriaQuery<Long> select = criteriaQuery
                .select(criteriaBuilder.count(ad))
                .where(getPredicate(pageableContext, criteriaQuery, criteriaBuilder, ad));

        return entityManager.createQuery(select).getSingleResult();
    }

    private Predicate getPredicate(PageableContext pageableContext, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<Ad> ad) {
        List<Predicate> predicates = new ArrayList<>();

        if (pageableContext.getMaxPrice() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(ad.get(Ad.PRICE_PATH), pageableContext.getMaxPrice()));
        }

        if (pageableContext.getMinPrice() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(ad.get(Ad.PRICE_PATH), pageableContext.getMinPrice()));
        }

        if (pageableContext.getCategoryId() != null) {
            Subquery<SubCategory> subQuery = criteriaQuery.subquery(SubCategory.class);

            Root<SubCategory> subCategory = subQuery.from(SubCategory.class);
            subQuery.select(subCategory.get(ID_PATH));
            subQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(
                                    subCategory.get(SubCategory.CATEGORY_PATH).get(ID_PATH),
                                    pageableContext.getCategoryId()
                            )
                    )
            );

            predicates.add(criteriaBuilder.in(ad.get(Ad.SUB_CATEGORY_PATH).get(ID_PATH)).value(subQuery));
        }

        if (pageableContext.getMetroId() != null) {
            predicates.add(criteriaBuilder.equal(ad.get(Ad.METRO_PATH).get(ID_PATH), pageableContext.getMetroId()));
        }
    
        if (pageableContext.getIsActive() != null) {
            predicates.add(criteriaBuilder.equal(ad.get(Ad.IS_ACTIVE_PATH), pageableContext.getIsActive()));
        }
    
        if (pageableContext.getTitle() != null) {
            predicates.add(criteriaBuilder.like(ad.get(Ad.TITLE_PATH), "%" + pageableContext.getTitle() + "%"));
        }
    
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
