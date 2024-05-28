package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.CateringOrder;
import java.time.LocalDate;
import org.springframework.data.repository.CrudRepository;

public interface CateringOrderRepository extends CrudRepository<CateringOrder, Integer> {
    
    int countDistinctCatererEmailByCreateDate(LocalDate date);
}
