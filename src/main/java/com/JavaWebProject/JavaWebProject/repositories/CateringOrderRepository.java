package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.CateringOrder;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CateringOrderRepository extends CrudRepository<CateringOrder, Integer> {
    
    int countDistinctCatererEmailByCreateDate(LocalDate date);
    
    int countDistinctCustomerEmailByCreateDate(LocalDate date);
    
    int countDistinctCustomerEmailByCreateDateBetween(LocalDate start, LocalDate end);
    
    @Query("select sum(u.pointDiscount) from CateringOrder u where u.createDate = ?1")
    Optional<Integer> sumPointDiscountByCreateDate(LocalDate date);
    
    int countByCreateDate(LocalDate date);
    
    int countByCreateDateBetween(LocalDate start, LocalDate end);
    
    @Query(value = "select count(*) from CateringOrder where year(CreateDate) = ?1", nativeQuery = true)
    int countByCreateDateYear(int year);
}
