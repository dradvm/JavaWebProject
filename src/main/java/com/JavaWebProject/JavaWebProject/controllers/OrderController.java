/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.CateringOrder;
import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.models.Dish;
import com.JavaWebProject.JavaWebProject.models.Notification;
import com.JavaWebProject.JavaWebProject.models.OrderDetails;
import com.JavaWebProject.JavaWebProject.models.PaymentHistory;
import com.JavaWebProject.JavaWebProject.models.Report;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CateringOrderService;
import com.JavaWebProject.JavaWebProject.services.CloudStorageService;
import com.JavaWebProject.JavaWebProject.services.CustomerService;
import com.JavaWebProject.JavaWebProject.services.DishService;
import com.JavaWebProject.JavaWebProject.services.NotificationService;
import java.time.LocalDateTime;
import com.JavaWebProject.JavaWebProject.services.PaymentService;
import com.JavaWebProject.JavaWebProject.services.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ReportService reportService;
    
    private Caterer findCaterer(String fullName_Email) {
        System.out.println(fullName_Email);
        String[] data = fullName_Email.split("_");
        data[0] = data[0].replace("-", " ");
        return catererService.findByCatererEmailAndFullName(data[0], data[1]);
    }
    
    @PostMapping("/changeState")
    public String changeState(@RequestParam("id") Integer orderId, @RequestParam("state") String state) {
        CateringOrder order = cateringOrderService.findByID(orderId);
        Caterer caterer = order.getCatererEmail();
        Customer customer = order.getCustomerEmail();
        if (state.equals("Cancelled")) {
            cateringOrderService.changeStateOrder(orderId, state);
            sendNoti(customer.getCustomerEmail(), caterer.getCatererEmail(), "Customer " + customer.getFullName() + " has cancelled order");
            customer.setPoint(customer.getPoint() + order.getPointDiscount());
            customerService.save(customer);
        }
        if (user.getRole().equals("Customer")) {
            if (state.equals("Waiting confirm")) {
                cateringOrderService.changeStateOrder(orderId, state);
                sendNoti(customer.getCustomerEmail(), caterer.getCatererEmail(), "Customer " + customer.getFullName() +" conducted payment for order");
                
            }
            else if (state.equals("Finished")) {
                cateringOrderService.changeStateOrder(orderId, state);
                sendNoti(customer.getCustomerEmail(), caterer.getCatererEmail(), "Customer " + customer.getFullName() +" has received the order");
                customer.setRollChance(customer.getRollChance() + 1);
                caterer.setPoint(caterer.getPoint() + order.getPointDiscount());
                catererService.save(caterer);
            }
            return "redirect:/customer/orders";
        }
        else if (user.getRole().equals("Caterer")) {
            if (state.equals("Accepted")) {
                if (caterer.getRankID().getRankCPO() == 0) {
                    cateringOrderService.changeStateOrder(orderId, state);
                    sendNoti(caterer.getCatererEmail(), customer.getCustomerEmail(), "We've received your order, please conduct payment for the order via NCB 0123456798 with transaction details Plate Portal order");

                    return "redirect:/caterer/myCaterer/orders";
                }
                else {
                    long fee = (long) (caterer.getRankID().getRankCPO() * 25000);
                    try {
                        String returnUrl = "http://localhost:8080/orders/accept/order_" + orderId;
                        String url = paymentService.getPaymentUrl(fee, returnUrl, request);
                        return "redirect:" + url;
                    }
                    catch (Exception e) {
                        return "redirect:/caterer/myCaterer/orders";
                    }
                }
            }
            else if (state.equals("Paid")) {
                cateringOrderService.changeStateOrder(orderId, state);
                sendNoti(caterer.getCatererEmail(), customer.getCustomerEmail(),  "The order is on its way, please confirm upon receipt");

                return "redirect:/caterer/myCaterer/orders";
            }
        }
        return "redirect:/";
    }
    @PostMapping("/changeState/reject")
    public String changeStateReject(@RequestParam("id") Integer orderId, @RequestParam("state") String state, @RequestParam("reason") String reason, ModelMap model) {
        cateringOrderService.changeStateOrder(orderId, state);
        CateringOrder od = cateringOrderService.findByID(orderId);
        sendNoti(od.getCatererEmail().getCatererEmail(), od.getCustomerEmail().getCustomerEmail(), reason);
        return catererController.orderPage(model);
    }
    @PostMapping("/changeState/report")
    public String changeStateReport(@RequestParam("reporter") String reporter, @RequestParam("reported") String reported, @RequestParam("reasonReport") String reason, ModelMap model) {
        Report report = new Report();
        report.setReportDate(new Date(LocalDate.now().getYear() - 1900, LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth()));
        report.setReporter(reporter);
        report.setReportee(reported);
        report.setReportDescription(reason);
        report.setReportStatus(0);
        reportService.save(report);
        return catererController.orderPage(model);
    }
    private void sendNoti(String sender, String receiver, String reason) {
        Notification noti = new Notification();
        noti.setNotificationContents(reason);
        noti.setSender(sender);
        noti.setReceiver(receiver);
        noti.setNotificationTime(LocalDateTime.now());
        notificationService.save(noti);
    }
    @GetMapping("/accept/{order_id}")
    public String accept(@PathVariable("order_id") String order_id) {
        if (paymentService.check(request)) {
            PaymentHistory payment = new PaymentHistory();
            int id = Integer.parseInt(order_id.split("_")[1]);
            CateringOrder order = cateringOrderService.findByID(id);
            order.setOrderState("Accepted");
            cateringOrderService.save(order);
            sendNoti(order.getCatererEmail().getCatererEmail(), order.getCustomerEmail().getCustomerEmail(), "We've received your order, please conduct payment for the order via NCB 0123456798 with transaction details Plate Portal order");

            payment.setCatererEmail(order.getCatererEmail());
            payment.setDescription("Order accept " + id);
            payment.setValue(order.getCatererEmail().getRankID().getRankCPO());
            payment.setPaymentTime(new Date());
            payment.setTypeID(paymentService.findPaymentTypeById(2));
            paymentService.savePaymentHistory(payment);
        }
        return "redirect:/caterer/myCaterer/orders";
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
 