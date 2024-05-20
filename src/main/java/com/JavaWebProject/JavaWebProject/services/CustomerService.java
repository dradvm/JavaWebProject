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

    public Customer findByCustomerEmail(String customerEmail) {
        return customerRepository.findByCustomerEmail(customerEmail);
    }

    public int getNewCustomerByDay(LocalDate date) {
        return customerRepository.countByCreateDate(date);
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
}
