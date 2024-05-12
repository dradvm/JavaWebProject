/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Customers;
import com.JavaWebProject.JavaWebProject.repositories.CustomerRepository;
import java.util.List;
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

    public List<Customers> getAllCustomers() {
        return (List<Customers>) customerRepository.findAll();
    }

    public Customers getCustomerById(String username) {
        return customerRepository.findByUsername(username);
    }
}
