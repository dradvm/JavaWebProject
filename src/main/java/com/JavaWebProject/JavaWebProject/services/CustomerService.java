/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.repositories.CustomerRepository;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer findById(String customerEmail) {
        Optional<Customer> result = customerRepository.findById(customerEmail);
        return result.isPresent() ? result.get() : null;
    }

    public int getNewCustomerByDay(LocalDate date) {
        return customerRepository.countByCreateDate(date);
    }
    public float getGapPercentCustomerByDay(LocalDate date) {
        float currentValue = getNewCustomerByDay(date);
        float oldValue = getNewCustomerByDay(date.minusDays(1));
        if (oldValue == 0) {
            if (currentValue == 0) {
                return 0;
            }
            return 1;
        }
        return ((currentValue/oldValue) - 1) * 100;
    }
    public int getNewCustomerByMonth(Month month) {
        int year = LocalDate.now().getYear();
        if (month.getValue() > LocalDate.now().getMonthValue()) {
            year--;
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();
        return customerRepository.countByCreateDateBetween(startDate, endDate);
    }
    public int getNewCustomerByYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return customerRepository.countByCreateDateBetween(startDate, endDate);
    }
<<<<<<< HEAD
    public void updateRollChance(String username, Integer rollChance) {
        Customer customer = findByCustomerEmail(username);
        customer.setRollChance(rollChance);
        customerRepository.save(customer);
    }
    public void updatePointValue(String username, Integer point) {
        Customer customer = findByCustomerEmail(username);
        customer.setPoint(customer.getPoint() + point);
=======
    
    public void save(Customer customer) {
>>>>>>> bff4e3874fc590742ef4ce720b9188324f1e6b98
        customerRepository.save(customer);
    }
}
