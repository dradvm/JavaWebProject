/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.Customer;
import java.time.LocalDate;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author DELL
 */
public interface CustomerRepository extends CrudRepository<Customer, String> {
    
    int countByCreateDate(LocalDate createDate);
    
    int countByCreateDateBetween(LocalDate startDate, LocalDate endDate);
}