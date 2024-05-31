/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Feedback;
import com.JavaWebProject.JavaWebProject.repositories.FeedbackRepository;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public void save(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    public Feedback findById(int id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public void deleteById(int id) {
        feedbackRepository.deleteById(id);
    }
    public int getNewFeedbackByDay(LocalDate date) {
        return feedbackRepository.countByFeedbackDate(date);
    }

    public float getGapPercentFeedbackByDay(LocalDate date) {
        float currentValue = getNewFeedbackByDay(date);
        float oldValue = getNewFeedbackByDay(date.minusDays(1));
        if (oldValue == 0) {
            if (currentValue == 0) {
                return 0;
            }
            return 100;
        }
        return ((currentValue / oldValue) - 1) * 100;
    }

    public int getNewFeedbackByMonth(Month month) {
        int year = LocalDate.now().getYear();
        if (month.getValue() > LocalDate.now().getMonthValue()) {
            year--;
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();
        return feedbackRepository.countByFeedbackDateBetween(startDate, endDate);
    }

    public int getNewFeedbackByYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return feedbackRepository.countByFeedbackDateBetween(startDate, endDate);
    }

}
