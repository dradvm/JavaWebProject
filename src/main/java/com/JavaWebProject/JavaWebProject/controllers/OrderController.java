/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.CateringOrder;
import com.JavaWebProject.JavaWebProject.models.Dish;
import com.JavaWebProject.JavaWebProject.models.Notification;
import com.JavaWebProject.JavaWebProject.models.OrderDetails;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CateringOrderService;
import com.JavaWebProject.JavaWebProject.services.CloudStorageService;
import com.JavaWebProject.JavaWebProject.services.DishService;
import com.JavaWebProject.JavaWebProject.services.NotificationService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    CateringOrderService cateringOrderService;
    @Autowired
    AuthController user;
    @Autowired
    CustomerController customerController;
    @Autowired
    CatererController catererController;
    @Autowired
    DishService dishService;
    @Autowired
    CloudStorageService cloudStorageService;
    @Autowired
    NotificationService notificationService;
    
    private Caterer findCaterer(String fullName_Email) {
        System.out.println(fullName_Email);
        String[] data = fullName_Email.split("_");
        data[0] = data[0].replace("-", " ");
        return catererService.findByCatererEmailAndFullName(data[0], data[1]);
    }
    
    @PostMapping("/changeState")
    public String changeState(@RequestParam("id") Integer orderId, @RequestParam("state") String state, ModelMap model) {
        cateringOrderService.changeStateOrder(orderId, state);  
        if (user.getRole().equals("Customer")) {
            return customerController.ordersCustomerPage(model);
        }
        else if (user.getRole().equals("Caterer")) {
            return catererController.orderPage(model);
        }
        else {
            model.addAttribute("selectedNav", "home");
            return "index";
        }
    }
    @PostMapping("/changeState/reject")
    public String changeStateReject(@RequestParam("id") Integer orderId, @RequestParam("state") String state, @RequestParam("reason") String reason, ModelMap model) {
        cateringOrderService.changeStateOrder(orderId, state);
        CateringOrder od = cateringOrderService.findByID(orderId);
        Notification noti = new Notification();
        noti.setNotificationContents(reason);
        noti.setSender(od.getCatererEmail().getCatererEmail());
        noti.setReceiver(od.getCustomerEmail().getCustomerEmail());
        noti.setNotificationTime(LocalDateTime.now());
        notificationService.save(noti);
        return catererController.orderPage(model);
    }
    @GetMapping("/getOrderDetails")
    @ResponseBody
    public Map<String, Object> getOrderDetails(@RequestParam("id") int id) {
        CateringOrder co = cateringOrderService.findByID(id);
        Map<String, Object> data = new HashMap<>();
        data.put("address", co.getOrderAddress() + ", " + co.getDistrictID().getDistrictName() + ", " + co.getDistrictID().getCityID().getCityName());
        data.put("point", co.getPointDiscount());
        data.put("voucher", co.getVoucherDiscount());
        Map<String, Object> temp = new HashMap<>();
        ArrayList orderDetails = new ArrayList<>();
        for (OrderDetails od :  co.getOrderDetailsCollection()) {
            temp = new HashMap<>();
            temp.put("dishName", od.getDish().getDishName());
            temp.put("dishImage", cloudStorageService.getDishImg(od.getDish().getDishImage()));
            temp.put("dishPrice", od.getPrice());
            temp.put("quantity", od.getQuantity());
            orderDetails.add(temp);
        }
        data.put("orderDetails", orderDetails );
        return data;
    }
}
 