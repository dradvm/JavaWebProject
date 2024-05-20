/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CustomerService;
import com.JavaWebProject.JavaWebProject.services.PaymentService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private CatererService catererService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PaymentService paymentService;
    
    private ArrayList<LocalDate> days;
    private ArrayList<Month> months;
    private ArrayList<Integer> years;
    private ArrayList<String> labelsDay;
    private ArrayList<String> labelsMonth;
    private ArrayList<String> labelsYear;
    private LocalDate today;
    @GetMapping(value = "/dashboard")
    public String adminPage(ModelMap model) {
        days = new ArrayList<>();
        months = new ArrayList<>();
        years = new ArrayList<>();
        today = LocalDate.now();
        for (int i = 6; i >=0; i--) {
            days.add(today.minusDays(i));
            months.add(today.getMonth().minus(i));
            years.add(today.getYear() - i);
        }
        labelsDay = days.stream().map(day -> 
            String.valueOf(day.getDayOfMonth())+
            "/"+
            String.valueOf(day.getMonthValue())
        ).collect(Collectors.toCollection(ArrayList::new));
        labelsMonth = months.stream().map(month ->
            String.valueOf(month.getValue())
        ).collect(Collectors.toCollection(ArrayList::new));
        labelsYear = years.stream().map(year ->
            String.valueOf(year)
        ).collect(Collectors.toCollection(ArrayList::new));
        int newCatererToday = catererService.getNewCatererByDay(LocalDate.now());
        int newCustomerToday = customerService.getNewCustomerByDay(LocalDate.now());
        int newOrderToday = paymentService.getNewOrderToday();
        float revenueToday = paymentService.getTotalValueToday();
        model.addAttribute("newCatererToday", newCatererToday);
        model.addAttribute("newCustomerToday", newCustomerToday);
        model.addAttribute("newOrderToday", newOrderToday);
        model.addAttribute("revenueToday", revenueToday);
        
        return "AdminPage/admindashboard";
    }
    @GetMapping("/lineChart")
    @ResponseBody
    public Map<String, Object> getDataLineChart(@RequestParam("selectedValue") String selectedValue) {
        // Xử lý logic dựa trên selectedValue
        Map<String, Object> data = new HashMap<>();
        ArrayList<Integer> dataChart = new ArrayList<>();
        switch (selectedValue) {
            case "Day":
                data.put("labels", labelsDay);
                for (LocalDate day : days) {
                    dataChart.add(catererService.getNewCatererByDay(day) + customerService.getNewCustomerByDay(day) );
                }
                break;
            case "Month":
                data.put("labels", labelsMonth);
                for (Month month : months) {
                    dataChart.add(catererService.getNewCatererByMonth(month) + customerService.getNewCustomerByMonth(month));
                }
                break;
            case "Year":
                data.put("labels", labelsYear);
                for (int year : years) {
                    dataChart.add(catererService.getNewCatererByYear(year) + customerService.getNewCustomerByYear(year));
                }
                break;
            default:
                data.put("labels", new String[]{});
                data.put("data", new int[]{});
                break;
        }
        data.put("data", dataChart);
        return data; // Chỉ trả về fragment cần cập nhật
    }
    @GetMapping("/polarAreaChart")
    @ResponseBody Map<String, Object> getDataPolarAreaChart() {
        Map<String, Object> data = new HashMap<>();
        data.put ("labels", paymentService.getLabelsList());
        data.put("data", paymentService.getValueByDate(LocalDate.now()).stream().mapToDouble(Float::doubleValue));
        return data;
    }
    @GetMapping("/barChart")
    @ResponseBody
    public Map<String, Object> getDataBarChart(@RequestParam("selectedValue") String selectedValue) {
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> datasets = new ArrayList<>();
        switch (selectedValue) {
            case "Day":
                data.put("labels", labelsDay);
                datasets = paymentService.getValueBarChartInRange(days);
                break;
            case "Month":
                data.put("labels", labelsMonth);
                datasets = paymentService.getValueBarChartInRange(months);
                break;
            case "Year":
                data.put("labels", labelsYear);
                datasets = paymentService.getValueBarChartInRange(years);
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
