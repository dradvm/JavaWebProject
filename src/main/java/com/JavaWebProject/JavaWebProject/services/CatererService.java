/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.repositories.CatererRepository;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class CatererService {
    @Autowired
    private CatererRepository catererRepository;
    
    public Caterer findById(String catererEmail) {
        Optional<Caterer> result = catererRepository.findById(catererEmail);
        return result.isPresent() ? result.get() : null;
    }
    
    public List<Caterer> findAll() {
        return catererRepository.findAll();
    }
    
    public int getNewCatererByDay(LocalDate date) {
        return catererRepository.countByCreateDate(date);
    }
    public float getGapPercentCatererByDay(LocalDate date) {
        float currentValue = getNewCatererByDay(date);
        float oldValue = getNewCatererByDay(date.minusDays(1));
        if (oldValue == 0) {
            if (currentValue == 0) {
                return 0;
            }
            return 100;
        }
        return ((currentValue/oldValue) - 1) * 100;
    }
    public int getNewCatererByMonth(Month month) {
        int year = LocalDate.now().getYear();
        if (month.getValue() > LocalDate.now().getMonthValue()) {
            year--;
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();
        return catererRepository.countByCreateDateBetween(startDate, endDate);
    }
    public int getNewCatererByYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return catererRepository.countByCreateDateBetween(startDate, endDate);
    }
    
    public void save(Caterer caterer) {
        catererRepository.save(caterer);
    }
}
