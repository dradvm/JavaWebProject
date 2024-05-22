/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.PaymentHistory;
import com.JavaWebProject.JavaWebProject.models.PaymentType;
import com.JavaWebProject.JavaWebProject.repositories.PaymentHistoryRepository;
import com.JavaWebProject.JavaWebProject.repositories.PaymentTypeRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class PaymentService {
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;
    public ArrayList<Float> getValueByDay(LocalDate date) {
        List<PaymentType> listLabels = paymentTypeRepository.findAll();
        ArrayList<Float> returnData = new ArrayList<>();
        LocalDateTime startDate = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(date, LocalTime.MAX);
        for (PaymentType label: listLabels) {
            List<PaymentHistory> listPaymentByLabel = paymentHistoryRepository.findByTypeIDAndPaymentTimeBetween(label, startDate, endDate);
            float sum = 0;
            for (PaymentHistory paymentHistory: listPaymentByLabel) {
                sum+= paymentHistory.getValue();
            }
            returnData.add(sum);
        }
        return returnData;
    }
    public ArrayList<String> getLabelsList() {
        ArrayList<String> listLabels = paymentTypeRepository.findAll().stream().map(PaymentType::getDescription).collect(Collectors.toCollection(ArrayList::new));
        return listLabels;
    }
    
    public List<Map<String, Object>> getValueBarChartInRange(ArrayList listDateLabels) {
        List<Map<String, Object>> datasets = new ArrayList<>();
        List<PaymentType> listLabels = paymentTypeRepository.findAll();
        for (PaymentType paymentType : listLabels) {
            Map<String, Object> set = new HashMap<>();
            ArrayList<Float> data = new ArrayList<>();
            for (Object valueDate: listDateLabels) {
                LocalDateTime startDate;
                LocalDateTime endDate;
                if (valueDate instanceof LocalDate) {
                    startDate = LocalDateTime.of((LocalDate) valueDate, LocalTime.MIN);
                    endDate = LocalDateTime.of((LocalDate) valueDate, LocalTime.MAX);
                }
                else if (valueDate instanceof Month) {
                    int year = LocalDate.now().getYear();
                    int value = ((Month) valueDate).getValue();
                    if (value > LocalDate.now().getMonthValue()) {
                        year--;
                    }
                    startDate = LocalDateTime.of(LocalDate.of(year, value, 1), LocalTime.MIN);
                    endDate = LocalDateTime.of(YearMonth.of(year, value).atEndOfMonth(), LocalTime.MAX);
                }
                else {
                    int value = (int) valueDate;
                    startDate = LocalDateTime.of(LocalDate.of(value, 1, 1), LocalTime.MIN);
                    endDate = LocalDateTime.of(LocalDate.of(value, 12, 31), LocalTime.MAX);
                }
                float sum = 0;
                List<PaymentHistory> paymentList = paymentHistoryRepository.findByTypeIDAndPaymentTimeBetween(paymentType, startDate, endDate);
                for (PaymentHistory paymentHistory : paymentList) {
                    sum+=paymentHistory.getValue();
                }
                data.add(sum);
            }
            set.put("label", paymentType.getDescription());
            set.put("data", data);
            datasets.add(set);
        
        }
        return datasets;
    }
    
    
    public float getTotalValueByDay(LocalDate date) {
        float sum = 0;
        for (Float item : getValueByDay(date)) {
            sum+=item;
        }
        return sum;
    }
    public float getGapPercentRevenueByDay(LocalDate date) {
        float currentValue = getTotalValueByDay(date);
        float oldValue = getTotalValueByDay(date.minusDays(1));
        if (oldValue == 0) {
            if (currentValue == 0) {
                return 0;
            }
            return 1;
        }
        return ((currentValue/oldValue) - 1) * 100;
    }
    
    public int getNewOrderByDay(LocalDate date) {
        return paymentHistoryRepository.countByTypeIDAndPaymentTimeBetween( paymentTypeRepository.findById(3) ,LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));

    }
    public float getGapPercentOrderByDay(LocalDate date) {
       float currentValue = getNewOrderByDay(date);
       float oldValue = getNewOrderByDay(date.minusDays(1));
        if (oldValue == 0) {
            if (currentValue == 0) {
                return 0;
            }
            return 1;
        }
       return ((currentValue/oldValue) - 1) * 100;
    }
}
