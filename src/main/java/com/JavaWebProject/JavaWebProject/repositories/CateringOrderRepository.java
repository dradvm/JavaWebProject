package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.CateringOrder;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CateringOrderRepository extends CrudRepository<CateringOrder, Integer> {
    
    int countDistinctCatererEmailByCreateDate(LocalDate date);
    
    int countByCreateDate(LocalDate date);
    
    int countByCreateDateBetween(LocalDate start, LocalDate end);
    
    @Query(value = "select count(*) from CateringOrder where year(CreateDate) = ?1", nativeQuery = true)
    int countByCreateDateYear(int year);
}
