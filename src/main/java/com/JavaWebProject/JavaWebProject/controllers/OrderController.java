/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author DELL
 */
@Controller
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    CatererService catererService;
    
    
    
    
    private Caterer findCaterer(String fullName_Email) {
        System.out.println(fullName_Email);
        String[] data = fullName_Email.split("_");
        data[0] = data[0].replace("-", " ");
        return catererService.findByCatererEmailAndFullName(data[0], data[1]);
    }
    
    @PostMapping("/makeNewOrder")
    public ResponseEntity<Map<String, Object>> makeNewOrder(@RequestBody Map<String, Object> data) {
        List<Map<String, Object>> listDish = (List<Map<String, Object>>) data.get("data");
        String fullNameEmail = String.valueOf(data.get("fullNameEmail"));
        for (Map<String, Object> item : listDish) {
            System.out.println(item.get("id"));
        }
        System.out.println(fullNameEmail);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "success");
        return ResponseEntity.ok(response);
    }
}
