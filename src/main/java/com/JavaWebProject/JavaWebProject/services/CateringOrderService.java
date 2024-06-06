package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.CateringOrder;
import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.repositories.CateringOrderRepository;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
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
    
    public int countTotalOrderFinished(Caterer caterer) {
        return cateringOrderRepository.findAllByCatererEmailAndOrderState(caterer, "Finished").size();
    }
    public int countTotalOrderCancelled(Caterer caterer) {
        return cateringOrderRepository.findAllByCatererEmailAndOrderState(caterer, "Cancelled").size();
    }
    public double countTotalRevenue(Caterer caterer) {
        double value = 0;
        for (CateringOrder co : cateringOrderRepository.findAllByCatererEmailAndOrderState(caterer, "Finished")) {
            value+= co.getValue();
        }
        return value;
    }
    public int getNewOrderByDay(Caterer caterer, LocalDate date) {
        return cateringOrderRepository.findAllByCatererEmailAndCreateDate(caterer, date).size();
    }

    public int getNewOrderByMonth(Caterer caterer, Month month) {
        int year = LocalDate.now().getYear();
        if (month.getValue() > LocalDate.now().getMonthValue()) {
            year--;
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();
        return cateringOrderRepository.findAllByCatererEmailAndCreateDateBetween(caterer, startDate, endDate).size();
    }

    public int getNewOrderByYear(Caterer caterer, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return cateringOrderRepository.findAllByCatererEmailAndCreateDateBetween(caterer, startDate, endDate).size();
    }
    
    public double getRevenueByDay(Caterer caterer, LocalDate date) {
        double value = 0;
        for (CateringOrder co : cateringOrderRepository.findAllByCatererEmailAndOrderStateAndCreateDate(caterer, "Finished" , date)) {
            value+=co.getValue();
        }
        return value;
    }

    public double getRevenueByMonth(Caterer caterer, Month month) {
        int year = LocalDate.now().getYear();
        if (month.getValue() > LocalDate.now().getMonthValue()) {
            year--;
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();
        double value = 0;
        for (CateringOrder co : cateringOrderRepository.findAllByCatererEmailAndOrderStateAndCreateDateBetween(caterer,"Finished", startDate, endDate)) {
            value+=co.getValue();
        }
        return value;
    }

    public double getRevenueByYear(Caterer caterer, int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        double value = 0;
        for (CateringOrder co : cateringOrderRepository.findAllByCatererEmailAndOrderStateAndCreateDateBetween(caterer,"Finished", startDate, endDate)) {
            value+=co.getValue();
        }
        return value;
    }
    public void save(CateringOrder order) {
        cateringOrderRepository.save(order);
    }
    
    public int maxID() {
        Optional<Integer> result = cateringOrderRepository.maxID();
        return result.isPresent() ? result.get() : 1;
    }
}
