package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Admin;
import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.services.AdminService;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CustomerService;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope(scopeName = "session")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CatererService catererService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AdminService adminService;
    private String role;
    private String username;
    
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin() {
        return "AuthPage/login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        password = hash(password);
        Customer customer = customerService.findByCustomerEmail(username);
        if (customer != null) {
            if (customer.getPassword().equals(password)) {
                if (customer.getActive() == 0) {
                    return "Suspended";
                }
                this.username = username;
                role = "Customer";
                return "Customer";
            }
            else {
                return "Fail";
            }
        }
        Caterer caterer = catererService.findByCatererEmail(username);
        if (caterer != null) {
            if (caterer.getPassword().equals(password)) {
                if (caterer.getActive() == 0) {
                    return "Suspended";
                }
                this.username = username;
                role = "Caterer";
                return "Caterer";
            }
            else {
                return "Fail";
            }
        }
        Admin admin = adminService.findByAdminUsername(username);
        if (admin != null && admin.getAdminPassword().equals(password)) {
            this.username = username;
            role = "Admin";
            return "Admin";
        }
        return "Fail";
    }
    
    private String hash(String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (Exception e) {
            return null;
        }
        BigInteger number = new BigInteger(1, md.digest(str.getBytes(StandardCharsets.UTF_8)));
        StringBuilder hex = new StringBuilder(number.toString(16));
        while (hex.length() < 64) {
            hex.insert(0, '0');
        }
        return hex.toString();
    }

    public String getUsername() {
        return username;
    }
    
    public String getRole() {
        return role;
    }
}
