/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CustomerService;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
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
    private CatererService catererService;
    @GetMapping("/hello")
    public String hello(ModelMap model) {
//        model.addAttribute("name", catererService.getNewCustomerByDay(LocalDate.of(2022, 1, 1)));
        return "hello";
    }
}
