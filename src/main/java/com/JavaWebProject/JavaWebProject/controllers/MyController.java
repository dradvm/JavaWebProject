/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author DELL
 */
@Controller
public class MyController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("/hello")
    public String hello(ModelMap model) {
        model.addAttribute("name", customerService.getCustomerById("ndhunga22008@cusc.ctu.edu.vn").getFullName());
        return "hello";
    }
}
