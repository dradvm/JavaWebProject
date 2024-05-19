/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author DELL
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {  
    private ArrayList<LocalDate> days;
    private ArrayList<Month> months;
    private ArrayList<Integer> years;
    private String[] labelsDay;
    private String[] labelsMonth;
    private String[] labelsYear;
    @GetMapping(value = "/dashboard")
    public String adminPage(Model model) {
        days = new ArrayList<>();
        months = new ArrayList<>();
        years = new ArrayList<>();
        for (int i = 6; i >=0; i--) {
            days.add(LocalDate.now().minusDays(i));
            months.add(LocalDate.now().getMonth().minus(i));
            years.add(LocalDate.now().getYear() - i);
        }
        labelsDay = days.stream().map(day -> 
            String.valueOf(day.getDayOfMonth())+
            "/"+
            String.valueOf(day.getMonthValue())
        ).toArray(String[]::new);
        labelsMonth = months.stream().map(month ->
            String.valueOf(month.getValue())
        ).toArray(String[]::new);
        labelsYear = years.stream().map(year ->
            String.valueOf(year)
        ).toArray(String[]::new);
        return "AdminPage/admindashboard";
    }
    @GetMapping("/lineChart")
    @ResponseBody
    public Map<String, Object> getDataLineChart(@RequestParam("selectedValue") String selectedValue) {
        // Xử lý logic dựa trên selectedValue
        Map<String, Object> data = new HashMap<>();
        switch (selectedValue) {
            case "Day":
                data.put("labels", labelsDay);
                data.put("data", new int[]{100, 50, 100});
                break;
            case "Month":
                data.put("labels", labelsMonth);
                data.put("data", new int[]{40, 200, 60});
                break;
            case "Year":
                data.put("labels", labelsYear);
                data.put("data", new int[]{70, 500, 90});
                break;
            default:
                data.put("labels", new String[]{});
                data.put("data", new int[]{});
                break;
        }
        return data; // Chỉ trả về fragment cần cập nhật
    }
    @GetMapping("/polarAreaChart")
    @ResponseBody
    public Object getDataPolarAreaChart() {
        return new int[]{1000000, 2000000, 3000000};
    }
    @GetMapping("/barChart")
    @ResponseBody
    public Map<String, Object> getDataBarChart(@RequestParam("selectedValue") String selectedValue) {
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> datasets = new ArrayList<>();
        Map<String, Object> commission = new HashMap<>();
        commission.put("label", "Commission");
        Map<String, Object> advertise = new HashMap<>();
        advertise.put("label", "Advertise");
        Map<String, Object> rank = new HashMap<>();
        rank.put("label", "Rank");
        datasets.add(advertise);
        datasets.add(rank);
        datasets.add(commission);
        switch (selectedValue) {
            case "Day":
                data.put("labels", labelsDay);
                advertise.put("data", new int[]{1, 2, 3, 4, 5, 6});
                rank.put("data", new int[]{7, 8, 9, 10, 11, 12});
                commission.put("data", new int[]{1,1,1,1,1,1});
                break;
            case "Month":
                data.put("labels", labelsMonth);
                advertise.put("data", new int[]{1, 2, 3, 4, 5, 6});
                rank.put("data", new int[]{7, 8, 9, 10, 11, 12});
                commission.put("data", new int[]{1,1,1,1,1,1});
                break;
            case "Year":
                data.put("labels", labelsYear);
                advertise.put("data", new int[]{1, 2, 3, 4, 5, 6});
                rank.put("data", new int[]{7, 8, 9, 10, 11, 12});
                commission.put("data", new int[]{1,1,1,1,1,1});
                break;
            default:
                data.put("labels", new String[]{});
                data.put("datasets", new int[]{});
                break;
        }
        data.put("datasets", datasets);
        return data;
    }
}
