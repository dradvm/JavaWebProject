package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Admin;
import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.models.District;
import com.JavaWebProject.JavaWebProject.services.AdminService;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CityService;
import com.JavaWebProject.JavaWebProject.services.CustomerService;
import com.JavaWebProject.JavaWebProject.services.DistrictService;
import com.JavaWebProject.JavaWebProject.services.MailService;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CatererService catererService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private MailService mailService;
    @Autowired
    private CityService cityService;
    @Autowired
    private DistrictService districtService;
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
    
    @RequestMapping(value = "/toSignup", method = RequestMethod.GET)
    public String toSignup(ModelMap model) {
        Customer customer = new Customer();
        customer.setDistrictID(new District());
        Caterer caterer = new Caterer();
        caterer.setDistrictID(new District());
        model.addAttribute("customer", customer);
        model.addAttribute("caterer", customer);
        model.addAttribute("cityList", cityService.findAll());
        model.addAttribute("districtList", districtService.findAll());
        return "/AuthPage/signup";
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        username = null;
        role = null;
        return "redirect:/";
    }
    
    private String hash(String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (Exception e) {
            e.printStackTrace();
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
