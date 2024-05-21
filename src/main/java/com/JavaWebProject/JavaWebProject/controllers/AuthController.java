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
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
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
    private Customer newCustomer;
    private Caterer newCaterer;
    private Instant expireTime;
    private int code;
    
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
        newCustomer = null;
        newCaterer = null;
        expireTime = null;
        code = 0;
        model.addAttribute("cityList", cityService.findAll());
        model.addAttribute("districtList", districtService.findAll());
        return "/AuthPage/signup";
    }
    
    @RequestMapping(value = "/signupCustomer", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> signupCustomer(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("gender") int gender,
            @RequestParam("birthday") String birthday,
            @RequestParam("address") String address,
            @RequestParam("district") int district) {
        Map<String, String> result = new HashMap<>();
        boolean valid = true;
        Pattern pattern = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))");
        if (email == null || !pattern.matcher(email).matches()) {
            valid = false;
            result.put("email", "fail");
        }
        if (password == null || password.trim().length() < 8) {
            valid = false;
            result.put("password", "fail");
        }
        if (name == null || name.trim().length() == 0) {
            valid = false;
            result.put("name", "fail");
        }
        pattern = Pattern.compile("^(?:[0-9] ?){7,10}$");
        if (phone == null || !pattern.matcher(phone).matches()) {
            valid = false;
            result.put("phone", "fail");
        }
        if (gender != 0 && gender != 1) {
            valid = false;
            result.put("gender", "fail");
        }
        Date date = new Date();
        if (birthday != null && birthday.trim().length() > 0) {
            String[] arr = birthday.split("-");
            try {
                int year = Integer.parseInt(arr[0]);
                int month = Integer.parseInt(arr[1]);
                int day = Integer.parseInt(arr[2]);
                date = new Date(year - 1990, month - 1, day);
            }
            catch (Exception e) {
                e.printStackTrace();
                date = null;
                valid = false;
                result.put("general", "fail");
            }
        }
        if (address == null || address.trim().length() == 0) {
            valid = false;
            result.put("address", "fail");
        }
        District districtID = districtService.findByDistrictID(district);
        if (districtID == null) {
            valid = false;
            result.put("general", "fail");
        }
        if (customerService.findByCustomerEmail(email) != null || catererService.findByCatererEmail(email) != null || adminService.findByAdminUsername(email) != null) {
            valid = false;
            result.put("general", "used");
        }
        if (!valid) {
            result.put("status", "Fail");
        }
        else {
            newCustomer = new Customer();
            newCustomer.setCustomerEmail(email);
            newCustomer.setPassword(hash(password));
            newCustomer.setFullName(name);
            newCustomer.setPoint(0.0);
            newCustomer.setRollChance(0);
            newCustomer.setActive(1);
            newCustomer.setFullName(name);
            newCustomer.setPhone(phone);
            newCustomer.setGender(gender);
            newCustomer.setAddress(address);
            if (date != null) {
                newCustomer.setBirthday(date);
            }
            newCustomer.setCreateDate(new Date());
            newCustomer.setDistrictID(districtID);
            result.put("status", "OK");
        }
        return result;
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
