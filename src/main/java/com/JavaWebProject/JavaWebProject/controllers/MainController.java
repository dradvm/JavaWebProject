package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.CateringOrder;
import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.models.DeliveryAddress;
import com.JavaWebProject.JavaWebProject.models.Dish;
import com.JavaWebProject.JavaWebProject.models.District;
import com.JavaWebProject.JavaWebProject.models.Notification;
import com.JavaWebProject.JavaWebProject.models.OrderDetails;
import com.JavaWebProject.JavaWebProject.models.OrderDetailsPK;
import com.JavaWebProject.JavaWebProject.models.Voucher;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CateringOrderService;
import com.JavaWebProject.JavaWebProject.services.CloudStorageService;
import com.JavaWebProject.JavaWebProject.services.CustomerService;
import com.JavaWebProject.JavaWebProject.services.DeliveryAddressService;
import com.JavaWebProject.JavaWebProject.services.DishService;
import com.JavaWebProject.JavaWebProject.services.DistrictService;
import com.JavaWebProject.JavaWebProject.services.NotificationService;
import com.JavaWebProject.JavaWebProject.services.OrderDetailsService;
import com.JavaWebProject.JavaWebProject.services.VoucherService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.SessionScope;

@Controller
@SessionScope
public class MainController {
    @Autowired
    private CatererService catererService;
    @Autowired
    private DishService dishService;
    @Autowired
    private AuthController authController;
    @Autowired
    private CloudStorageService cloudStorageService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private HttpSession session;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private DeliveryAddressService deliveryAddressService;
    @Autowired
    private CateringOrderService cateringOrderService;
    @Autowired
    private OrderDetailsService orderDetailsService;
    @Autowired
    private NotificationService notificationService; 
    private Caterer caterer;
    private List<Dish> catererDish;
    private Map<Dish, Integer> orderList;
    
    
    public void setTabNavBar(ModelMap model, String page) {
        model.addAttribute("selectedNav", page);
    }
    
    @GetMapping(value = "/")
    public String mainPage(ModelMap model) {
        setTabNavBar(model, "home");
        return "index";
    }
    
    @GetMapping(value = "/listCaterer")
    public String listCatererPage(ModelMap model) {
        setTabNavBar(model, "listCaterer");
        return "CustomerPage/customerlistcaterer";
    }
    
    @GetMapping(value = "/getListCaterer")
    @ResponseBody
    public ArrayList getListCaterer() {
        ArrayList data = new ArrayList();
        Map<String, Object> temp = new HashMap<>();
        for (Caterer c : catererService.findAllCatererActive()) {
            temp = new HashMap<>();
            temp.put("fullName", c.getFullName().replace(" ", "-"));
            temp.put("catererEmail", authController.hash_public(c.getCatererEmail()));
            temp.put("catererRating", c.getCatererRating());
            if (c.getDescription().length() > 50) {
                temp.put("description", c.getDescription().substring(0, 50)+"...");
            }
            else {
                temp.put("description", c.getDescription());
            }
            
            temp.put("image", cloudStorageService.getProfileImg("Caterer", c.getProfileImage()));
            data.add(temp);
        }
        return data;
    }
    
    private Caterer findCaterer(String fullName_Email) {
        System.out.println(fullName_Email);
        if (fullName_Email.equals(null)) {
            return new Caterer();
        }
        String[] data = fullName_Email.split("_");
        data[0] = data[0].replace("-", " ");
        return catererService.findByCatererEmailAndFullName(data[0], data[1]);
    }
    
    @GetMapping("/listCaterer/{fullName_Email}")
    public String detailsCatererPage(@PathVariable("fullName_Email") String fullName_Email, ModelMap model) {
        
        Caterer c = findCaterer(fullName_Email);
        model.addAttribute("profileImg", cloudStorageService.getProfileImg("Caterer", c.getProfileImage()));
        model.addAttribute("caterer", c);
        model.addAttribute("listDish", getDetailsCaterer(fullName_Email));
        
        caterer = findCaterer(fullName_Email);
        catererDish = dishService.findAllDishOfCaterer(caterer, 1);
        orderList = new HashMap();  
        for (Dish dish : catererDish) {
            dish.setDishImage(cloudStorageService.getDishImg(dish.getDishImage()));
            orderList.put(dish, 0);
        }
        model.addAttribute("dishList", catererDish);
        model.addAttribute("districtList", districtService.findAll());
        AuthController auth = (AuthController) session.getAttribute("scopedTarget.authController");
        List<DeliveryAddress> addressList = new ArrayList<>();
        if (auth != null && auth.getUsername() != null) {
            model.addAttribute("voucherList", voucherService.findAllVoucherAvailable(caterer, customerService.findById(auth.getUsername())));

            addressList = deliveryAddressService.findByCustomerEmail(customerService.findById(auth.getUsername()));
        }
        else {
            model.addAttribute("voucherList", new ArrayList<>());
        }
        
        model.addAttribute("addressList", addressList.isEmpty() ? null : addressList);
        return "CustomerPage/customerdetailscaterer";
    }
    
    @GetMapping("/getCatererDish/{fullName_Email}")
    @ResponseBody
    public ArrayList getDetailsCaterer(@PathVariable("fullName_Email") String fullName_Email) {
        ArrayList data = new ArrayList<>();
        Map<String, Object> temp = new HashMap<>();
        System.out.println(fullName_Email + "B");
        for (Dish d : dishService.findAllDishOfCaterer(findCaterer(fullName_Email), 1)) {
            temp = new HashMap<>();
            temp.put("name", d.getDishName());
            temp.put("price", d.getDishPrice());
            if (d.getDishDescription().length() > 50) {
                temp.put("des", d.getDishDescription().substring(0, 50) + "...");
            }
            else {
                temp.put("des", d.getDishDescription());
            }
            temp.put("tooltip", d.getDishDescription());
            temp.put("image", cloudStorageService.getDishImg(d.getDishImage()));
            temp.put("id", d.getDishID());
            data.add(temp);
        }
        return data;
    }
    
    @GetMapping("/getCatererVoucher/{fullName_Email}")
    @ResponseBody
    public ArrayList getCatererVoucher(@PathVariable("fullName_Email") String fullName_Email) {
        ArrayList data = new ArrayList<>();
        Map<String, Object> temp = new HashMap<>();
        AuthController auth = (AuthController) session.getAttribute("scopedTarget.authController");
        Customer customer = null;
        if (auth != null && auth.getUsername() != null) {
            customer = customerService.findById(auth.getUsername());
        }
        for (Voucher v : voucherService.findAllVoucherAvailable(findCaterer(fullName_Email), customer)) {
            temp = new HashMap<>();
            temp.put("ID", v.getVoucherID());
            temp.put("type", v.getTypeID().getTypeID());
            temp.put("value", v.getVoucherValue());
            temp.put("maxValue", v.getMaxValue());
            data.add(temp);
        }
        return data;
    }
    
    @PostMapping("/addDish")
    @ResponseBody
    public void addDish(@RequestParam("id") int id, @RequestParam("quantity") int quantity) {
        Dish dishAdd = null;
        for (Dish dish : catererDish) {
            if (dish.getDishID() == id) {
                dishAdd = dish;
                break;
            }
        }
        if (dishAdd != null) {
            int newQuan = orderList.get(dishAdd) + quantity;
            orderList.put(dishAdd, newQuan);
        }
    }
    
    @PostMapping("/getOrderListFE")
    @ResponseBody
    public List<Map<String, Object>> getOrderListFE() {
        List<Map<String, Object>> result = new ArrayList();
        for (Dish dish : orderList.keySet()) {
            Map<String, Object> temp = new HashMap();
            temp.put("id", dish.getDishID());
            temp.put("name", dish.getDishName());
            temp.put("img", dish.getDishImage());
            temp.put("price", dish.getDishPrice());
            temp.put("quantity", orderList.get(dish));
            result.add(temp);
        }
        return result;
    }
    
    @GetMapping("/calcTotal")
    @ResponseBody
    public double calcTotal(@RequestParam("voucherID") int voucherID, @RequestParam("usePoint") int usePoint) {
        double result = 0;
        if (orderList != null) {
            for (Dish dish : orderList.keySet()) {
                if (orderList.get(dish) > 0) {
                    result += dish.getDishPrice() * orderList.get(dish);
                }
            }
            Voucher voucher = null;
            if (voucherID != 0) {
                voucher = voucherService.findById(voucherID);
            }
            if (voucher != null) {
                if (voucher.getTypeID().getTypeID() == 1) {
                    result -= voucher.getVoucherValue();
                }
                else if (voucher.getTypeID().getTypeID() == 2) {
                    double discount = result * voucher.getVoucherValue() / 100;
                    discount = discount > voucher.getMaxValue() ? voucher.getMaxValue() : discount;
                    result -= discount;
                }
                result = result > 0 ? result : 0;
            }
            if (usePoint == 1) {
                AuthController auth = (AuthController) session.getAttribute("scopedTarget.authController");
                Customer customer = customerService.findById(auth.getUsername());
                result = (result - customer.getPoint()) > 0 ? (result - customer.getPoint()) : 0;
            }
        }
        
        return result;
    }
    private void sendNoti(String sender, String receiver, String reason) {
        Notification noti = new Notification();
        noti.setNotificationContents(reason);
        noti.setSender(sender);
        noti.setReceiver(receiver);
        noti.setNotificationTime(LocalDateTime.now());
        notificationService.save(noti);
    }
    @PostMapping("/createOrder")
    @ResponseBody
    public Map<String, Object> createOrder(
            @RequestParam("voucherID") int voucherID,
            @RequestParam("usePoint") int usePoint,
            @RequestParam("orderTime") String orderTime,
            @RequestParam("addressType") int addressType,
            @RequestParam("address0") String address0,
            @RequestParam("districtID") int districtID,
            @RequestParam("address1") int address1,
            @RequestParam("note") String note) {
        Map<String, Object> result = new HashMap();
        AuthController auth = (AuthController) session.getAttribute("scopedTarget.authController");
        Customer customer = customerService.findById(auth.getUsername());
        Voucher voucher = null;
        if (voucherID != 0) {
            voucher = voucherService.findById(voucherID);
            if (voucher.getEndDate().isBefore(LocalDate.now())) {
                result.put("status", "Fail");
                return result;
            }
        }
        LocalDateTime deliveryTime = null;
        try {
            String date = orderTime.split(" ")[0];
            String time = orderTime.split(" ")[1];
            int year = Integer.parseInt(date.split("-")[0]);
            int month = Integer.parseInt(date.split("-")[1]);
            int day = Integer.parseInt(date.split("-")[2]);
            int hour = Integer.parseInt(time.split(":")[0]);
            int min = Integer.parseInt(time.split(":")[1]);
            LocalDate temp = LocalDate.of(year, month, day);
            LocalTime temp2 = LocalTime.of(hour, min, 0);
            deliveryTime = LocalDateTime.of(temp, temp2);
        }
        catch (Exception e) {
            result.put("status", "Fail");
            return result;
        }
        District district = null;
        if (addressType == 0) {
            if (address0 == null || address0.trim().length() == 0) {
                result.put("status", "Fail");
                return result;
            }
            district = districtService.findById(districtID);
            if (district == null) {
                result.put("status", "Fail");
                return result;
            }
        }
        else if (addressType == 1) {
            DeliveryAddress temp = deliveryAddressService.findById(address1);
            if (temp == null) {
                result.put("status", "Fail");
                return result;
            }
            address0 = temp.getAddress();
            district = temp.getDistrictID();
        }
        else {
            result.put("status", "Fail");
            return result;
        }
        CateringOrder order = new CateringOrder();
        order.setCatererEmail(caterer);
        order.setCustomerEmail(customer);
        order.setCreateDate(new Date());
        order.setDistrictID(district);
        order.setNote(note);
        order.setOrderAddress(address0);
        order.setOrderState("Waiting");
        order.setOrderTime(Date.from(deliveryTime.atZone(ZoneId.systemDefault()).toInstant()));
        order.setVoucherID(voucher);
        double value = 0;
        for (Dish dish : orderList.keySet()) {
            if (orderList.get(dish) > 0) {
                value += dish.getDishPrice() * orderList.get(dish);
            }
        }
        order.setValue(value);
        if (voucher != null) {
            if (voucher.getTypeID().getTypeID() == 1) {
                double discount = value > voucher.getVoucherValue() ? voucher.getVoucherValue() : value;
                order.setVoucherDiscount(discount);
            }
            else if (voucher.getTypeID().getTypeID() == 2) {
                double discount = value * voucher.getVoucherValue() / 100;
                discount = discount > voucher.getMaxValue() ? voucher.getMaxValue() : discount;
                order.setVoucherDiscount(discount);
            }
        }
        else {
            order.setVoucherDiscount(0.0);
        }
        if (usePoint == 1) {
            double currentValue = order.getValue() - order.getVoucherDiscount();
            int point = customer.getPoint();
            if (point > currentValue) {
                order.setPointDiscount((int) Math.ceil(currentValue));
                customer.setPoint(customer.getPoint() - ((int) Math.ceil(currentValue)));
            }
            else {
                order.setPointDiscount(point);
                customer.setPoint(0);
            }
        }
        else {
            order.setPointDiscount(0);
        }
        cateringOrderService.save(order);
        customerService.save(customer);
        catererService.save(caterer);
        sendNoti(customer.getCustomerEmail(), caterer.getCatererEmail(), "Customer "+ customer.getFullName() + " has ordered a catering service");
        int orderID = cateringOrderService.maxID();
        for (Dish dish : orderList.keySet()) {
            if (orderList.get(dish) > 0) {
                OrderDetailsPK pk = new OrderDetailsPK(dish.getDishID(), orderID);
                OrderDetails od = new OrderDetails(pk, orderList.get(dish), dish.getDishPrice());
                orderDetailsService.save(od);
            }
        }
        result.put("status", "OK");
        result.put("target", "/listCaterer");
        return result;
    }

    public Caterer getCaterer() {
        return caterer;
    }

    public void setCaterer(Caterer caterer) {
        this.caterer = caterer;
    }

    public List<Dish> getCatererDish() {
        return catererDish;
    }

    public void setCatererDish(List<Dish> catererDish) {
        this.catererDish = catererDish;
    }

    public Map<Dish, Integer> getOrderList() {
        return orderList;
    }

    public void setOrderList(Map<Dish, Integer> orderList) {
        this.orderList = orderList;
    }
}
