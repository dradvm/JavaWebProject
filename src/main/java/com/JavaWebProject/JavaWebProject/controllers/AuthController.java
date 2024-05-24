package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Admin;
import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.models.District;
import com.JavaWebProject.JavaWebProject.models.PaymentHistory;
import com.JavaWebProject.JavaWebProject.services.AdminService;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CityService;
import com.JavaWebProject.JavaWebProject.services.CustomerService;
import com.JavaWebProject.JavaWebProject.services.DistrictService;
import com.JavaWebProject.JavaWebProject.services.MailService;
import com.JavaWebProject.JavaWebProject.services.PaymentService;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
    @Autowired
    private PaymentService paymentService;
    private String role;
    private String username;
    private Customer newCustomer;
    private Caterer newCaterer;
    private String retrieveEmail;
    private Instant expireTime;
    private int code;
    
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin() {
        role = null;
        username = null;
        newCustomer = null;
        newCaterer = null;
        retrieveEmail = null;
        expireTime = null;
        code = 0;
        return "AuthPage/login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        password = hash(password);
        Customer customer = customerService.findById(username);
        if (customer != null) {
            if (customer.getPassword().equals(password)) {
                if (customer.getActive() == 0) {
                    return "Suspended";
                }
                this.username = username;
                role = "Customer";
                return "/";
            }
            else {
                return "Fail";
            }
        }
        Caterer caterer = catererService.findById(username);
        if (caterer != null) {
            if (caterer.getPassword().equals(password)) {
                if (caterer.getActive() == 0) {
                    return "Suspended";
                }
                this.username = username;
                role = "Caterer";
                return "/";
            }
            else {
                return "Fail";
            }
        }
        Admin admin = adminService.findById(username);
        if (admin != null && admin.getAdminPassword().equals(password)) {
            this.username = username;
            role = "Admin";
            return "/admin/dashboard";
        }
        return "Fail";
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        username = null;
        role = null;
        return "redirect:/";
    }
    
    @RequestMapping(value = "/toRetrievepassword", method = RequestMethod.GET)
    public String toRetrievepassword() {
        retrieveEmail = null;
        newCustomer = null;
        newCaterer = null;
        expireTime = null;
        code = 0;
        return "/AuthPage/retrievepassword";
    }
    
    @RequestMapping(value = "/checkRetrieveEmail", method = RequestMethod.POST)
    @ResponseBody
    public String checkRetrieveEmail(@RequestParam("email") String email) {
        Pattern pattern = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))");
        if (email == null || !pattern.matcher(email).matches()) {
            return "Email";
        }
        if (customerService.findById(email) == null && catererService.findById(email) == null && adminService.findById(email) == null) {
            return "Unregistered";
        }
        retrieveEmail = email;
        return "/auth/toEmailverificationRetrieve";
    }
    
    @RequestMapping(value = "/toEmailverificationRetrieve", method = RequestMethod.GET)
    public String toEmailverificationRetrieve(ModelMap model) {
        model.addAttribute("command", "retrieve");
        model.addAttribute("email", retrieveEmail);
        expireTime = Instant.now().plus(Duration.ofMinutes(5));
        Random random = new Random();
        code = random.nextInt(100000, 1000000);
        mailService.sendMail(retrieveEmail, "Plate Portal verification code", "Your email verification code is " + code + ", it is effective in 5 minutes");
        return "/AuthPage/emailverification";
    }
    
    @RequestMapping(value = "/verifyEmailRetrieve", method = RequestMethod.POST)
    @ResponseBody
    public String verifyEmailRetrieve(@RequestParam("code") int code) {
        if (Instant.now().isAfter(expireTime)) {
            return "Expired";
        }
        else if (code != this.code) {
            return "Incorrect";
        }
        else {
            expireTime = null;
            this.code = -1;
            return "/auth/toResetpassword";
        }
    }
    
    @RequestMapping(value = "/toResetpassword", method = RequestMethod.GET)
    public String toResetpassword() {
        if (code != -1 || retrieveEmail == null) {
            return "redirect:/";
        }
        return "/AuthPage/resetpassword";
    }
    
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public String resetPassword(@RequestParam("password") String password) {
        if (code != -1 || retrieveEmail == null) {
            retrieveEmail = null;
            code = 0;
            return "Fail";
        }
        if (password == null || password.trim().length() < 8) {
            return "Invalid";
        }
        Customer customer = customerService.findById(retrieveEmail);
        if (customer != null) {
            customer.setPassword(hash(password));
            customerService.save(customer);
            retrieveEmail = null;
            return "OK";
        }
        Caterer caterer = catererService.findById(retrieveEmail);
        if (caterer != null) {
            caterer.setPassword(hash(password));
            catererService.save(caterer);
            retrieveEmail = null;
            return "OK";
        }
        Admin admin = adminService.findById(retrieveEmail);
        if (admin != null) {
            admin.setAdminPassword(hash(password));
            adminService.save(admin);
            retrieveEmail = null;
            return "OK";
        }
        retrieveEmail = null;
        code = 0;
        return "Fail";
    }
    
    @RequestMapping(value = "/toSignup", method = RequestMethod.GET)
    public String toSignup(ModelMap model) {
        retrieveEmail = null;
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
                date = new Date(year - 1900, month - 1, day);
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
        District districtID = districtService.findById(district);
        if (districtID == null) {
            valid = false;
            result.put("general", "fail");
        }
        if (customerService.findById(email) != null || catererService.findById(email) != null || adminService.findById(email) != null) {
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
            result.put("target", "/auth/toEmailverificationSignup");
        }
        return result;
    }
    
    @RequestMapping(value = "/signupCaterer", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> signupCaterer(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("gender") int gender,
            @RequestParam("birthday") String birthday,
            @RequestParam("address") String address,
            @RequestParam("district") int district,
            @RequestParam("description") String description) {
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
                date = new Date(year - 1900, month - 1, day);
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
        District districtID = districtService.findById(district);
        if (districtID == null) {
            valid = false;
            result.put("general", "fail");
        }
        if (customerService.findById(email) != null || catererService.findById(email) != null || adminService.findById(email) != null) {
            valid = false;
            result.put("general", "used");
        }
        if (!valid) {
            result.put("status", "Fail");
        }
        else {
            newCaterer = new Caterer();
            newCaterer.setCatererEmail(email);
            newCaterer.setPassword(hash(password));
            newCaterer.setFullName(name);
            newCaterer.setActive(1);
            newCaterer.setFullName(name);
            newCaterer.setPhone(phone);
            newCaterer.setGender(gender);
            newCaterer.setAddress(address);
            if (description != null && description.trim().length() > 0) {
                newCaterer.setDescription(description);
            }
            if (date != null) {
                newCaterer.setBirthday(date);
            }
            newCaterer.setCreateDate(new Date());
            newCaterer.setDistrictID(districtID);
            result.put("status", "OK");
            result.put("target", "/auth/toEmailverificationSignup");
        }
        return result;
    }
    
    @RequestMapping(value = "/toEmailverificationSignup", method = RequestMethod.GET)
    public String toEmailverificationSignup(ModelMap model) {
        model.addAttribute("command", "signup");
        String email = newCustomer != null ? newCustomer.getCustomerEmail() : newCaterer.getCatererEmail();
        model.addAttribute("email", email);
        expireTime = Instant.now().plus(Duration.ofMinutes(5));
        Random random = new Random();
        code = random.nextInt(100000, 1000000);
        mailService.sendMail(email, "Plate Portal verification code", "Your email verification code is " + code + ", it is effective in 5 minutes.");
        return "/AuthPage/emailverification";
    }
    
    @RequestMapping(value = "/verifyEmailSignup", method = RequestMethod.POST)
    @ResponseBody
    public String verifyEmailSignup(@RequestParam("code") int code) {
        if (Instant.now().isAfter(expireTime)) {
            return "Expired";
        }
        else if (code != this.code) {
            return "Incorrect";
        }
        else {
            expireTime = null;
            if (newCustomer != null) {
                this.code = 0;
                customerService.save(newCustomer);
                username = newCustomer.getCustomerEmail();
                role = "Customer";
                newCustomer = null;
                newCaterer = null;
                return "/";
            }
            else if (newCaterer != null) {
                this.code = -1;
                return "/rank/toBuyrankSignup";
            }
        }
        return "Fail";
    }
    
    @RequestMapping(value = "/completeSignupCaterer", method = RequestMethod.GET)
    public String completeSignupCaterer() {
        code = 0;
        Date current = new Date();
        newCaterer.setRankStartDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        newCaterer.setRankEndDate(calendar.getTime());
        username = newCaterer.getCatererEmail();
        role = "Caterer";
        catererService.save(newCaterer);
        PaymentHistory payment = new PaymentHistory();
        payment.setCatererEmail(newCaterer);
        payment.setDescription("Rank buy " + newCaterer.getRankID().getRankID());
        payment.setPaymentTime(current);
        payment.setTypeID(paymentService.findPaymentTypeById(1));
        payment.setValue(newCaterer.getRankID().getRankFee());
        paymentService.savePaymentHistory(payment);
        newCaterer = null;
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

    public Customer getNewCustomer() {
        return newCustomer;
    }

    public Caterer getNewCaterer() {
        return newCaterer;
    }

    public String getRetrieveEmail() {
        return retrieveEmail;
    }

    public int getCode() {
        return code;
    }
}
