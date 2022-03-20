package com.spbstu.edu.advertisement.repository.impl;

import com.spbstu.edu.advertisement.entity.Ad;
import com.spbstu.edu.advertisement.entity.SubCategory;
import com.spbstu.edu.advertisement.vo.PageableContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import static com.spbstu.edu.advertisement.entity.Ad.ID_PATH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PageableAdRepositoryImplTest {
    
    enum Mode {
        FIND,
        COUNT
    }
    
    @Mock
    private EntityManager entityManager;
    
    @InjectMocks
    private PageableAdRepositoryImpl pageableAdRepository;
    
    @Mock
    private CriteriaBuilder criteriaBuilder;
    
    @Mock
    private CriteriaQuery<Ad> criteriaQueryAd;
    
    @Mock
    private Root<Ad> ad;
    
    @Mock
    private Subquery<SubCategory> subQuery;
    
    @Mock
    private Root<SubCategory> subCategory;
    
    @Mock
    private TypedQuery<Ad> typedQueryAd;
    
    @Mock
    private Predicate predicate;
    
    @Mock
    private CriteriaBuilder.In<Object> builderIn;
    
    @Mock
    private Path<Object> path;
    
    @Mock
    private Expression<Long> expression;
    
    @Mock
    private CriteriaQuery<Long> criteriaQueryLong;
    
    
    @Mock
    private TypedQuery<Long> typedQueryLong;
    
    private int maxPriceCounter = 0;
    private int minPriceCounter = 0;
    private int metroIdCounter = 0;
    private int categoryIdCounter = 0;
    private int titleCounter = 0;
    private int isActiveCounter = 0;
    
    
    @Test
    void findAdsByFilters() {
        Double maxPrice = 12.0;
        Double minPrice = 2.0;
        Long metroId = 1L;
        String title = "title";
        Boolean isActive = true;
        Long categoryId = 2L; // not equals metroId
        
        Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        Mockito.when(criteriaBuilder.createQuery(Ad.class)).thenReturn(criteriaQueryAd);
        Mockito.when(criteriaQueryAd.from(Ad.class)).thenReturn(ad);
        
        Mockito.when(criteriaQueryAd.select(ad)).thenReturn(criteriaQueryAd);
        Mockito.when(criteriaQueryAd.where(any(Predicate.class))).thenReturn(criteriaQueryAd);
        Mockito.when(criteriaQueryAd.orderBy(criteriaBuilder.desc(ad.get(ID_PATH)))).thenReturn(criteriaQueryAd);
        Mockito.when(entityManager.createQuery(criteriaQueryAd)).thenReturn(typedQueryAd);
        
        Mockito.when(criteriaQueryAd.subquery(SubCategory.class)).thenReturn(subQuery);
        
        mockPredicates(maxPrice, minPrice, metroId, title, isActive, categoryId);
        
        
        verifyFindAdsByFilter(Mode.FIND, 1, minPrice, maxPrice, metroId, categoryId, isActive, title);
        verifyFindAdsByFilter(Mode.FIND, 1, null, null, null, null, null, null);
        verifyFindAdsByFilter(Mode.FIND, 1, minPrice, null, metroId, categoryId, null, title);
        
        
        verify(criteriaBuilder, times(maxPriceCounter)).lessThanOrEqualTo(ad.get(Ad.PRICE_PATH), maxPrice);
        verify(criteriaBuilder, times(minPriceCounter)).greaterThanOrEqualTo(ad.get(Ad.PRICE_PATH), minPrice);
        verify(criteriaBuilder, times(metroIdCounter)).equal(ad.get(Ad.METRO_PATH).get(ID_PATH), metroId);
        verify(criteriaBuilder, times(isActiveCounter)).equal(ad.get(Ad.IS_ACTIVE_PATH), isActive);
        verify(criteriaBuilder, times(titleCounter)).like(ad.get(Ad.TITLE_PATH), "%" + title + "%");
        verify(criteriaBuilder, times(categoryIdCounter)).in(ad.get(Ad.SUB_CATEGORY_PATH).get(ID_PATH));
        
    }
    
    @Test
    void countAdsByFilter() {
        Double maxPrice = 12.0;
        Double minPrice = 2.0;
        Long metroId = 1L;
        String title = "title";
        Boolean isActive = true;
        Long categoryId = 2L; // not equals metroId
        
        Mockito.when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        Mockito.when(criteriaBuilder.createQuery(Long.class)).thenReturn(criteriaQueryLong);
        Mockito.when(criteriaQueryLong.from(Ad.class)).thenReturn(ad);
        
        Mockito.when(criteriaBuilder.count(ad)).thenReturn(expression);
        
        Mockito.when(criteriaQueryLong.select(expression)).thenReturn(criteriaQueryLong);
        Mockito.when(criteriaQueryLong.where(any(Predicate.class))).thenReturn(criteriaQueryLong);
        
        Mockito.when(criteriaQueryLong.subquery(SubCategory.class)).thenReturn(subQuery);
        
        mockPredicates(maxPrice, minPrice, metroId, title, isActive, categoryId);
        
        
        Mockito.when(entityManager.createQuery(criteriaQueryLong)).thenReturn(typedQueryLong);
        
        
        verifyFindAdsByFilter(Mode.COUNT, 1, minPrice, maxPrice, metroId, categoryId, isActive, title);
        verifyFindAdsByFilter(Mode.COUNT, 1, null, null, null, null, null, null);
        verifyFindAdsByFilter(Mode.COUNT, 1, minPrice, null, metroId, categoryId, null, title);
        
        
        verify(criteriaBuilder, times(maxPriceCounter)).lessThanOrEqualTo(ad.get(Ad.PRICE_PATH), maxPrice);
        verify(criteriaBuilder, times(minPriceCounter)).greaterThanOrEqualTo(ad.get(Ad.PRICE_PATH), minPrice);
        verify(criteriaBuilder, times(metroIdCounter)).equal(ad.get(Ad.METRO_PATH).get(ID_PATH), metroId);
        verify(criteriaBuilder, times(isActiveCounter)).equal(ad.get(Ad.IS_ACTIVE_PATH), isActive);
        verify(criteriaBuilder, times(titleCounter)).like(ad.get(Ad.TITLE_PATH), "%" + title + "%");
        verify(criteriaBuilder, times(categoryIdCounter)).in(ad.get(Ad.SUB_CATEGORY_PATH).get(ID_PATH));
    }
    
    
    private void mockPredicates(Double maxPrice, Double minPrice, Long metroId, String title, Boolean isActive, Long categoryId) {
        Mockito.when(criteriaBuilder.lessThanOrEqualTo(ad.get(Ad.PRICE_PATH), maxPrice)).thenReturn(predicate);
        Mockito.when(criteriaBuilder.greaterThanOrEqualTo(ad.get(Ad.PRICE_PATH), minPrice)).thenReturn(predicate);
        
        Mockito.when(subQuery.from(SubCategory.class)).thenReturn(subCategory);
        Mockito.when(subCategory.get(SubCategory.CATEGORY_PATH)).thenReturn(path);
        Mockito.when(criteriaBuilder.equal(subCategory.get(SubCategory.CATEGORY_PATH).get(ID_PATH), categoryId)).thenReturn(predicate);
        Mockito.when(ad.get(Ad.SUB_CATEGORY_PATH)).thenReturn(path);
        Mockito.when(criteriaBuilder.in(ad.get(Ad.SUB_CATEGORY_PATH).get(ID_PATH))).thenReturn(builderIn);
        Mockito.when(builderIn.value(subQuery)).thenReturn(builderIn);
        
        Mockito.when(ad.get(Ad.METRO_PATH)).thenReturn(path);
        Mockito.when(criteriaBuilder.equal(ad.get(Ad.METRO_PATH).get(ID_PATH), metroId)).thenReturn(predicate);
        Mockito.when(criteriaBuilder.equal(ad.get(Ad.IS_ACTIVE_PATH), isActive)).thenReturn(predicate);
        Mockito.when(criteriaBuilder.like(ad.get(Ad.TITLE_PATH), "%" + title + "%")).thenReturn(predicate);
        
        Mockito.when(criteriaBuilder.and(any())).thenReturn(predicate);
    }
    
    private void verifyFindAdsByFilter(Mode mode, Integer page, Double minPrice, Double maxPrice, Long metroId,
                                       Long categoryId, Boolean isActive, String title) {
        PageableContext pageableContext = PageableContext.builder()
                .page(page)
                .maxPrice(maxPrice)
                .minPrice(minPrice)
                .metroId(metroId)
                .categoryId(categoryId)
                .isActive(isActive)
                .title(title)
                .build();
        
        if (mode == Mode.FIND) {
            pageableAdRepository.findAdsByFilters(pageableContext);
        } else {
            pageableAdRepository.countAdsByFilter(pageableContext);
        }
        
        if (maxPrice != null) {
            maxPriceCounter++;
        }
        if (minPrice != null) {
            minPriceCounter++;
        }
        if (metroId != null) {
            metroIdCounter++;
        }
        if (isActive != null) {
            isActiveCounter++;
        }
        if (title != null) {
            titleCounter++;
        }
        if (categoryId != null) {
            categoryIdCounter++;
        }
    }
}
