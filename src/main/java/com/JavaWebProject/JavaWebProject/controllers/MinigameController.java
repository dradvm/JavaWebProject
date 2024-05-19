/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author DELL
 */
@Controller
@RequestMapping("/minigame")
public class MinigameController {
    @GetMapping("")
    public String minigamePage() {
        return "/MinigamePage/minigame";
    }
    
    @GetMapping("/getDataOfWheel")
    @ResponseBody
    public List<Map<String, Object>> getDataOfWheel() {
        Map<String, Object> obj1 = new HashMap<>();
        Map<String, Object> obj2 = new HashMap<>();
        Map<String, Object> obj3 = new HashMap<>();
        Map<String, Object> obj4 = new HashMap<>();
        Map<String, Object> obj5 = new HashMap<>();
        obj1.put("value", 100);
        obj1.put("weight", 100);
        obj2.put("value", 10000);
        obj2.put("weight", 1);
        obj3.put("value", 200);
        obj3.put("weight", 50);
        obj4.put("value", 1000);
        obj4.put("weight", 5);
        obj5.put("value", 500);
        obj5.put("weight", 10);
        return Arrays.asList(obj1, obj2, obj3, obj4, obj5,obj1, obj2, obj3, obj4, obj5);
    }
    
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestParam("value") int value) {
        System.out.println(value);
        // Trả về response
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("receivedNumber", value);
        return response;
    }
}
