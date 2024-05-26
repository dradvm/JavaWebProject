/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.services.CatererRankService;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CustomerService;
import com.JavaWebProject.JavaWebProject.services.DistrictService;
import com.JavaWebProject.JavaWebProject.services.PaymentService;
import com.JavaWebProject.JavaWebProject.services.RankManageService;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @Autowired
    private CatererRankService catererRankService;
    @Autowired
    private RankManageService rankManageService;
    @Autowired
    private DistrictService districtService;
    private ArrayList<LocalDate> days;
    private ArrayList<Month> months;
    private ArrayList<Integer> years;
    private ArrayList<String> labelsDay;
    private ArrayList<String> labelsMonth;
    private ArrayList<String> labelsYear;
    private LocalDate today;
    public void setTabAdminPage(ModelMap model, String page, String title) {
        model.addAttribute("selectedPage", page);
        model.addAttribute("title", title);
    }
    
    @GetMapping(value = "/dashboard")
    public String adminDashboardPage(ModelMap model) {
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
        model.addAttribute("newCatererToday",  catererService.getNewCatererByDay(today));
        model.addAttribute("newCustomerToday", customerService.getNewCustomerByDay(today));
        model.addAttribute("newOrderToday", paymentService.getNewOrderByDay(today));
        model.addAttribute("revenueToday", paymentService.getTotalValueByDay(today));
        model.addAttribute("gapPercentRevenue", paymentService.getGapPercentRevenueByDay(today));
        model.addAttribute("gapPercentOrder", paymentService.getGapPercentOrderByDay(today));
        model.addAttribute("gapPercentCustomer", customerService.getGapPercentCustomerByDay(today));
        model.addAttribute("gapPercentCaterer", catererService.getGapPercentCatererByDay(today));
        setTabAdminPage(model, "admindashboard", "Dashboard");
        return "AdminPage/admindashboard";
    }
    @GetMapping("/toManageinformationCaterer")
    public String toCatererlist(ModelMap model) {
        setTabAdminPage(model, "admincaterer", "Manage Caterer");
        model.addAttribute("catererList", catererService.findAll());
        return "AdminPage/Caterer/manageinformation";
    }
    
    @GetMapping("/changeCatererActive")
    public String changeCatererActive(@RequestParam("email") String email) {
        Caterer caterer = catererService.findById(email);
        if (caterer.getActive() == 0) {
            caterer.setActive(1);
        }
        else {
            caterer.setActive(0);
        }
        catererService.save(caterer);
        return "redirect:/admin/toManageinformationCaterer";
    }
    
    @GetMapping("/toEditinformationCaterer")
    public String toEditinformationCaterer(@RequestParam("email") String email, ModelMap model) {
        setTabAdminPage(model, "admincaterer", "Manage Caterer");
        model.addAttribute("caterer", catererService.findById(email));
        model.addAttribute("rankList", catererRankService.findAll());
        model.addAttribute("districtList", districtService.findAll());
        return "AdminPage/Caterer/editinformation";
    }
    
    @GetMapping("/manageCustomer")
    public String adminCustomerPage(ModelMap model) {
        setTabAdminPage(model, "admincustomer", "Manage Customer");
        return "AdminPage/admincustomer";
    }
    
//    @GetMapping("/manageCatererRank")
//    public String adminCatererRankPage(ModelMap model) {
//        setTabAdminPage(model, "admincatererrank", "Manage Caterer Rank");
//        return "AdminPage/admincatererrank";
//    }
    
    @GetMapping("/manageFeedback")
    public String adminFeedbackPage(ModelMap model) {
        setTabAdminPage(model, "adminfeedback", "Manage Feedback");
        return "AdminPage/adminfeedback";
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
        data.put("data", paymentService.getValueByDay(LocalDate.now()).stream().mapToDouble(Float::doubleValue));
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
    @GetMapping("/toManageCatererRanks")
    public String toCatererRanklist(ModelMap model) {
        setTabAdminPage(model, "admincatererrank", "Manage Caterer Rank");
        model.addAttribute("catererRankList", rankManageService.findAll());
        return "AdminPage/CatererRank/catererRanks";
    }
    
    
    
}
