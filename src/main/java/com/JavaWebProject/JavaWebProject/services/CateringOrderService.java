package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.repositories.CateringOrderRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CateringOrderService {
    @Autowired
    private CateringOrderRepository cateringOrderRepository;
    
    public int countDistinctCatererEmailByCreateDate(LocalDate date) {
        return cateringOrderRepository.countDistinctCatererEmailByCreateDate(date);
    }
    
    public int getNumCatererGotOrderedGapByDay(LocalDate date) {
        return countDistinctCatererEmailByCreateDate(date) - countDistinctCatererEmailByCreateDate(date.minusDays(1));
    }
}
