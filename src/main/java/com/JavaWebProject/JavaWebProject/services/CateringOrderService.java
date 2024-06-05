package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.CateringOrder;
import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.repositories.CateringOrderRepository;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CateringOrderService {
    @Autowired
    private CateringOrderRepository cateringOrderRepository;
    
    public int countDistinctCatererEmailByCreateDate(LocalDate date) {
        return cateringOrderRepository.countDistinctCatererEmailByCreateDate(date);
    }
    
    public int countDistinctCustomerEmailByCreateDate(LocalDate date) {
        return cateringOrderRepository.countDistinctCustomerEmailByCreateDate(date);
    }
    
    public int sumPointDiscountByCreateDate(LocalDate date) {
        Optional<Integer> result = cateringOrderRepository.sumPointDiscountByCreateDate(date);
        return result.isPresent() ? result.get() : 0;
    }
    
    public int getNumCatererGotOrderedGapByDay(LocalDate date) {
        return countDistinctCatererEmailByCreateDate(date) - countDistinctCatererEmailByCreateDate(date.minusDays(1));
    }
    
    public int getNumCustomerOrderedGapByDay(LocalDate date) {
        return countDistinctCustomerEmailByCreateDate(date) - countDistinctCustomerEmailByCreateDate(date.minusDays(1));
    }
    
    public int getPointUsedGapByDay(LocalDate date) {
        return sumPointDiscountByCreateDate(date) - sumPointDiscountByCreateDate(date.minusDays(1));
    }
    
    public int countByCreateDate(LocalDate date) {
        return cateringOrderRepository.countByCreateDate(date);
    }
    
    public int countByCreateDateMonth(Month month) {
        int year = LocalDate.now().getYear();
        if (month.getValue() > LocalDate.now().getMonthValue()) {
            year--;
        }
        LocalDate start = LocalDate.of(year, month, month.minLength());
        LocalDate end = LocalDate.of(year, month, month.maxLength());
        return cateringOrderRepository.countByCreateDateBetween(start, end);
    }
    
    public int countByCreateDateYear(int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);
        return cateringOrderRepository.countByCreateDateBetween(start, end);
    }
    
    public int getNewOrderGapByDay(LocalDate date) {
        return countByCreateDate(date) - countByCreateDate(date.minusDays(1));
    }
    
    public List<Map<String, Object>> getValueBarChartCustomer(ArrayList dates) {
        List<Map<String, Object>> datasets = new ArrayList();
        ArrayList<String> labels = new ArrayList();
        labels.add("New orders");
        labels.add("Customers ordered");
        for (String label : labels) {
            Map<String, Object> set = new HashMap();
            ArrayList<Integer> data = new ArrayList();
            for (Object valueDate : dates) {
                if (valueDate instanceof LocalDate) {
                    if (label.equals("New orders")) {
                        data.add(countByCreateDate((LocalDate) valueDate));
                    }
                    else {
                        data.add(countDistinctCustomerEmailByCreateDate((LocalDate) valueDate));
                    }
                }
                if (valueDate instanceof Month) {
                    Month month = (Month) valueDate;
                    if (label.equals("New orders")) {
                        data.add(countByCreateDateMonth(month));
                    }
                    else {
                        int year = LocalDate.now().getYear();
                        if (month.getValue() > LocalDate.now().getMonthValue()) {
                            year--;
                        }
                        LocalDate start = LocalDate.of(year, month, month.minLength());
                        LocalDate end = LocalDate.of(year, month, month.maxLength());
                        data.add(cateringOrderRepository.countDistinctCustomerEmailByCreateDateBetween(start, end));
                    }
                }
                else {
                    int year = (int) valueDate;
                    if (label.equals("New orders")) {
                        data.add(countByCreateDateYear(year));
                    }
                    else {
                        LocalDate start = LocalDate.of(year, 1, 1);
                        LocalDate end = LocalDate.of(year, 12, 31);
                        data.add(cateringOrderRepository.countDistinctCustomerEmailByCreateDateBetween(start, end));
                    }
                }
            }
            set.put("label", label);
            set.put("data", data);
            datasets.add(set);
        }
        return datasets;
    }
    
    public List<CateringOrder> findAllByCustomer(Customer customer) {
        return cateringOrderRepository.findAllByCustomerEmail(customer);
    }
}
