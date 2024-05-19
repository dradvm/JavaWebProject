/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.repositories.CatererRepository;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Date;
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
    
    public int getNewCatererByDay(LocalDate date) {
        return catererRepository.countByCreateDate(date);
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
}