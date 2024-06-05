package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.CateringOrder;
import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.models.DeliveryAddress;
import com.JavaWebProject.JavaWebProject.models.District;
import com.JavaWebProject.JavaWebProject.services.CateringOrderService;
import com.JavaWebProject.JavaWebProject.services.CloudStorageService;
import com.JavaWebProject.JavaWebProject.services.CustomerService;
import com.JavaWebProject.JavaWebProject.services.DeliveryAddressService;
import com.JavaWebProject.JavaWebProject.services.DistrictService;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
    @Autowired
    private HttpSession session;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CloudStorageService cloudStorageService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private CateringOrderService cateringOrderService;
    @Autowired
    private AuthController user;
    
    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editProfile(
            @RequestParam("profileImg") MultipartFile profileImg,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("gender") int gender,
            @RequestParam("address") String address,
            @RequestParam("districtID") int districtID,
            @RequestParam("birthday") String birthday) {
        Map<String, Object> result = new HashMap();
        AuthController auth = (AuthController) session.getAttribute("scopedTarget.authController");
        Customer customer = customerService.findById(auth.getUsername());
        if (!profileImg.isEmpty()) {
            String type = profileImg.getContentType();
            if (type == null || type.equals("application/octet-stream")) {
                result.put("status", "Invalid");
                return result;
            } else if (!type.equals("image/jpeg") && !type.equals("image/png")) {
                result.put("status", "Invalid");
                return result;
            }
            if (profileImg.getSize() > 10000000) {
                result.put("status", "Invalid");
                return result;
            }
        }
        if (name == null || name.trim().length() == 0) {
            result.put("status", "Invalid");
            return result;
        }
        Pattern pattern = Pattern.compile("^(?:[0-9] ?){7,11}$");
        if (phone == null || !pattern.matcher(phone).matches()) {
            result.put("status", "Invalid");
            return result;
        }
        if (gender != 0 && gender != 1) {
            result.put("status", "Invalid");
            return result;
        }
        if (address == null || address.trim().length() == 0) {
            result.put("status", "Invalid");
            return result;
        }
        District district = districtService.findById(districtID);
        if (district == null) {
            result.put("status", "Invalid");
            return result;
        }
        Date parsedBirthday = new Date();
        if (birthday != null && birthday.trim().length() > 0) {
            String[] arr = birthday.split("-");
            try {
                int year = Integer.parseInt(arr[0]);
                int month = Integer.parseInt(arr[1]);
                int day = Integer.parseInt(arr[2]);
                parsedBirthday = new Date(year - 1900, month - 1, day);
            } catch (Exception e) {
                e.printStackTrace();
                result.put("status", "Invalid");
                return result;
            }
        } 
        else {
            parsedBirthday = null;
        }
        if (!profileImg.isEmpty()) {
            if (customer.getProfileImage() != null) {
                if (!cloudStorageService.deleteFile("customer/" + customer.getProfileImage())) {
                    result.put("status", "Fail");
                    return result;
                }
            }
            String fileName = cloudStorageService.generateFileName(profileImg);
            if (cloudStorageService.uploadFile("customer/" + fileName, profileImg)) {
                customer.setProfileImage(fileName);
            } 
            else {
                result.put("status", "Fail");
                return result;
            }
        }
        customer.setFullName(name);
        customer.setPhone(phone);
        customer.setGender(gender);
        customer.setAddress(address);
        customer.setDistrictID(district);
        if (parsedBirthday != null) {
            customer.setBirthday(parsedBirthday);
        }
        customerService.save(customer);
        result.put("status", "OK");
        result.put("target", "/auth/toProfile");
        return result;
    }
    
    @RequestMapping(value = "/toDeliveryaddress", method = RequestMethod.GET)
    public String toDeliveryaddress(ModelMap model) {
        AuthController auth = (AuthController) session.getAttribute("scopedTarget.authController");
        Customer customer = customerService.findById(auth.getUsername());
        model.addAttribute("deliveryAddressList", deliveryAddressService.findByCustomerEmail(customer));
        return "CustomerPage/deliveryaddress";
    }
    
    @RequestMapping(value = "/toEditdeliveryaddress", method = RequestMethod.POST)
    public String toEditdeliveryaddress(ModelMap model, @RequestParam("id") int id) {
        AuthController auth = (AuthController) session.getAttribute("scopedTarget.authController");
        DeliveryAddress address = deliveryAddressService.findById(id);
        if (address == null || !address.getCustomerEmail().getCustomerEmail().equals(auth.getUsername())) {
            return "redirect:/customer/toDeliveraddress";
        }
        model.addAttribute("deliveryAddress", address);
        model.addAttribute("districtList", districtService.findAll());
        return "CustomerPage/editdeliveryaddress";
    }
    
    @RequestMapping(value = "/editDeliveryaddress", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editDeliveryaddress(
            @RequestParam("id") int id,
            @RequestParam("address") String address,
            @RequestParam("districtID") int districtID) {
        Map<String, Object> result = new HashMap();
        AuthController auth = (AuthController) session.getAttribute("scopedTarget.authController");
        DeliveryAddress addressEdit = deliveryAddressService.findById(id);
        if (addressEdit == null || !addressEdit.getCustomerEmail().getCustomerEmail().equals(auth.getUsername())) {
            result.put("status", "Fail");
            return result;
        }
        if (address == null || address.trim().length() == 0) {
            result.put("status", "Fail");
            return result;
        }
        District district = districtService.findById(districtID);
        if (district == null) {
            result.put("status", "Fail");
            return result;
        }
        addressEdit.setAddress(address);
        addressEdit.setDistrictID(district);
        deliveryAddressService.save(addressEdit);
        result.put("status", "OK");
        result.put("target", "/customer/toDeliveryaddress");
        return result;
    }
    
    @RequestMapping(value = "/toAdddeliveryaddress", method = RequestMethod.GET)
    public String toAdddeliveryaddress(ModelMap model) {
        model.addAttribute("districtList", districtService.findAll());
        return "CustomerPage/adddeliveryaddress";
    }
    
    @RequestMapping(value = "/addDeliveryaddress", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addDeliveryaddress(@RequestParam("address") String address, @RequestParam("districtID") int districtID) {
        Map<String, Object> result = new HashMap();
        if (address == null || address.trim().length() == 0) {
            result.put("status", "Fail");
            return result;
        }
        District district = districtService.findById(districtID);
        if (district == null) {
            result.put("status", "Fail");
            return result;
        }
        AuthController auth = (AuthController) session.getAttribute("scopedTarget.authController");
        Customer customer = customerService.findById(auth.getUsername());
        DeliveryAddress da = new DeliveryAddress();
        da.setAddress(address);
        da.setCustomerEmail(customer);
        da.setDistrictID(district);
        deliveryAddressService.save(da);
        result.put("status", "OK");
        result.put("target", "/customer/toDeliveryaddress");
        return result;
    }
    
    @RequestMapping(value = "/deleteDeliveryaddress", method = RequestMethod.POST)
    public String deleteDeliveryaddress(@RequestParam("id") int id) {
        AuthController auth = (AuthController) session.getAttribute("scopedTarget.authController");
        DeliveryAddress address = deliveryAddressService.findById(id);
        if (address != null && address.getCustomerEmail().getCustomerEmail().equals(auth.getUsername())) {
            deliveryAddressService.delete(address);
        }
        return "redirect:/customer/toDeliveryaddress";
    }


    @GetMapping("/orders")
    public String ordersCustomerPage(ModelMap model) {
        model.addAttribute("selectedNav", "orders" );
        model.addAttribute("listOrder", cateringOrderService.findAllByCustomer(customerService.findById(user.getUsername())));
        return "CustomerPage/customerorders";
    }
        
}
