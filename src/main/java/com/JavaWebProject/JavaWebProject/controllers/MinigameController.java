/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.MinigameReward;
import com.JavaWebProject.JavaWebProject.services.MinigameRewardService;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private MinigameRewardService minigameRewardService;
    private List<MinigameReward> data;
    @GetMapping("")
    public String minigamePage() {
        data = minigameRewardService.getAllMinigameReward();
        return "/MinigamePage/minigame";
    }
    
    @GetMapping("/getDataOfWheel")
    @ResponseBody
    public Object getDataOfWheel() {
        return data.stream().mapToDouble((minigameReward) -> minigameReward.getPoint()).toArray();
    }
    
    @GetMapping("/spin")
    @ResponseBody
    public int getValueSpin() {
        int total = 0;
        for (MinigameReward minigameReward : data) {
            total+=minigameReward.getWeight();
        }
        double random = Math.ceil(Math.random() * total);
        double cursor = 0;
        for (int i = 0; i< data.size(); i++) {
            cursor += data.get(i).getWeight();
            if (cursor >= random) {
                return (int) data.get(i).getPoint();
            }
        }
        return 0;
    }
    
    @PostMapping("/update")
    @ResponseBody
    public Map<String, Object> update(@RequestParam("value") int value) {
        System.out.println(value);
        // Trả về response
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        return response;
    }
}
