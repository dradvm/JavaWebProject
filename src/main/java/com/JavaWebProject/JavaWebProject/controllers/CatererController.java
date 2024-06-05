/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Banner;
import com.JavaWebProject.JavaWebProject.models.BannerType;
import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.Dish;
import com.JavaWebProject.JavaWebProject.models.District;
import com.JavaWebProject.JavaWebProject.services.BannerManageService;
import com.JavaWebProject.JavaWebProject.services.BannerTypeService;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CloudStorageService;
import com.JavaWebProject.JavaWebProject.services.DishService;
import com.JavaWebProject.JavaWebProject.services.DistrictService;
import com.JavaWebProject.JavaWebProject.services.OrderDetailsService;
import java.math.BigDecimal;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author DELL
 */
@Controller
@RequestMapping(value = "/caterer")
public class CatererController {
    @Autowired
    private AuthController user;
    @Autowired
    private CatererService catererService;
    @Autowired
    private DishService dishService;
    @Autowired
    private OrderDetailsService orderDetailsService;
    @Autowired
    private CloudStorageService cloudStorageService;
    @Autowired
    private HttpSession session;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private BannerManageService bannerManageService;
    @Autowired
    private BannerTypeService bannerTypeService;
    
    @GetMapping(value = "/myCaterer/orders")
    public String orderPage(ModelMap model) {
        model.addAttribute("selectedNav", "myCaterer");
        model.addAttribute("selectedPage", "catererorders");
        return "CatererPage/OrderPage/catererorders";
    }
    @GetMapping(value = "/myCaterer/dishes")
    public String dishPage(ModelMap model) {
        
        ArrayList data = new ArrayList<>();
        Map<String, Object> temp;
        if (user != null ) {
            for (Dish d : dishService.findAllDishOfCaterer(catererService.findById(user.getUsername()), 1)) {
                temp = new HashMap();
                temp.put("dish", d);
                temp.put("dishImage", cloudStorageService.getDishImg(d.getDishImage()));
                temp.put("dishName", d.getDishName());
                temp.put("dishPrice", d.getDishPrice());
                temp.put("dishDescription", d.getDishDescription());
                temp.put("dishID", d.getDishID());
                temp.put("dishOrder", orderDetailsService.countOrderSuccessWithDish(d.getDishID()));
                temp.put("dishQuantity", orderDetailsService.countQuanitySuccessWithDish(d.getDishID()));
                data.add(temp);
            }
        }
        model.addAttribute("dishes", data);
        model.addAttribute("selectedNav", "myCaterer");
        model.addAttribute("selectedPage", "catererdishes");
        return "CatererPage/DishesPage/catererdishes";
    }
    @GetMapping("/editDish")
    public String editDishPage(@RequestParam("ID") int dishID,ModelMap model) {
        Dish dish = dishService.findByDishID(dishID);
        dish.setDishImage(cloudStorageService.getDishImg(dish.getDishImage()));
        model.addAttribute("dish", dish);
        
        return addAndUpdateDishPage(model);
    }
    
    @GetMapping("/myCaterer/dishes/addAndUpdateDishPage")
    public String addAndUpdateDishPage(ModelMap model) {
        model.addAttribute("selectedNav", "myCaterer");
        model.addAttribute("selectedPage", "catererdishes");
        return "CatererPage/DishesPage/catererdishesaddupdate";
    }
        
    
    public void addAndUpdateDish(Dish dish, String dishName, MultipartFile dishImage, String dishDescription, double dishPrice ) {
        dish.setCatererEmail(catererService.findById(user.getUsername()));
        dish.setDishDescription(dishDescription);
        dish.setDishName(dishName);
        dish.setDishPrice(BigDecimal.valueOf(dishPrice));
        dish.setDishStatus(1);
        if (!dishImage.isEmpty()) {
            String fileName = cloudStorageService.generateFileName(dishImage, user.getUsername());
            dish.setDishImage(fileName);
            cloudStorageService.uploadFile("dish/" + fileName, dishImage);
        } 
        dishService.save(dish);
    }
    
    @PostMapping("/addDish")
    public String addDish(
            @RequestParam("dishName") String dishName,
            @RequestParam("dishImage") MultipartFile dishImage,
            @RequestParam("dishDescription") String dishDescription,
            @RequestParam("dishPrice") double dishPrice,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Dish dish = new Dish();
        
        if (dishService.isReachMaxDish(catererService.findById(user.getUsername()))) {
            redirectAttributes.addFlashAttribute("message", "You have reached max dish!!");
        }
        else {
            addAndUpdateDish(dish, dishName, dishImage, dishDescription, dishPrice);
        }
        
        
        return "redirect:/caterer/myCaterer/dishes";
    }
    @PostMapping("/updateDish")
    public String updateDish(
            @RequestParam("dishID") int dishID,
            @RequestParam("dishName") String dishName,
            @RequestParam("dishImage") MultipartFile dishImage,
            @RequestParam("dishDescription") String dishDescription,
            @RequestParam("dishPrice") double dishPrice,
            Model model
    ) {
        Dish dish = dishService.findByDishID(dishID);
        addAndUpdateDish(dish, dishName, dishImage, dishDescription, dishPrice);
        
        return "redirect:/caterer/myCaterer/dishes";
    }
    
    
    @GetMapping(value = "/deleteDish")
    public String deleteDish(@RequestParam int ID) {
        Dish dish = dishService.findByDishID(ID);
        if (orderDetailsService.isExistDishInOrderDetails(ID)) { 
            dish.setDishStatus(0);
            dishService.save(dish);
        }
        else {
            cloudStorageService.deleteFile("dish/" + dish.getDishImage());
            dishService.delete(dish);
        }
        return "redirect:/caterer/myCaterer/dishes";
    }
    
    @GetMapping(value = "/myCaterer/dishes/barChartDish")
    @ResponseBody
    public ArrayList getDataLineChartDish() {
        ArrayList data = new ArrayList<>();
        Map<String, Object> temp = new HashMap<>();
        for (Dish d : dishService.findAllDishOfCaterer(catererService.findById(user.getUsername()), 1)) {
            temp = new HashMap<>();
            temp.put("name", d.getDishName());
            temp.put("quantity", orderDetailsService.countQuanitySuccessWithDish(d.getDishID()));
            temp.put("order", orderDetailsService.countOrderSuccessWithDish(d.getDishID()));
            
            data.add(temp);
        }
        return data;
    }
    
    @GetMapping(value = "/myCaterer/dishes/report")
    public String reportPage(ModelMap model) {
        
        model.addAttribute("selectedNav", "myCaterer");
        model.addAttribute("selectedPage", "catererdishes");
        return "/CatererPage/DishesPage/catererdishesreport";
    }
    
    @PostMapping(value = "/editProfile")
    @ResponseBody
    public Map<String, Object> editProfile(
            @RequestParam("name") String name,
            @RequestParam("profileImg") MultipartFile profileImg,
            @RequestParam("paymentInformation") String paymentInformation,
            @RequestParam("description") String description,
            @RequestParam("phone") String phone,
            @RequestParam("gender") int gender,
            @RequestParam("address") String address,
            @RequestParam("districtID") int districtID,
            @RequestParam("birthday") String birthday) {
        Map<String, Object> result = new HashMap();
        AuthController auth = (AuthController) session.getAttribute("scopedTarget.authController");
        Caterer caterer = catererService.findById(auth.getUsername());
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
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+ ([0-9]+ ?){1,}$");
        if (paymentInformation == null || !pattern.matcher(paymentInformation).matches()) {
            result.put("status", "Invalid");
            return result;
        }
        pattern = Pattern.compile("^(?:[0-9] ?){7,11}$");
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
            if (caterer.getProfileImage() != null) {
                if (!cloudStorageService.deleteFile("caterer/" + caterer.getProfileImage())) {
                    result.put("status", "Fail");
                    return result;
                }
            }
            String fileName = cloudStorageService.generateFileName(profileImg);
            if (cloudStorageService.uploadFile("caterer/" + fileName, profileImg)) {
                caterer.setProfileImage(fileName);
            } 
            else {
                result.put("status", "Fail");
                return result;
            }
        }
        caterer.setFullName(name);
        caterer.setPaymentInformation(paymentInformation);
        caterer.setDescription(description);
        caterer.setPhone(phone);
        caterer.setGender(gender);
        caterer.setAddress(address);
        caterer.setDistrictID(district);
        if (parsedBirthday != null) {
            caterer.setBirthday(parsedBirthday);
        }
        catererService.save(caterer);
        result.put("status", "OK");
        result.put("target", "/auth/toProfile");
        return result;
    }
    @GetMapping("/myCaterer/banners")
    public String bannerPage(ModelMap model, HttpSession session) {

        ArrayList<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> temp;
        if (user != null) {
            for (Banner b : bannerManageService.findAllBannersByCaterer(catererService.findById(user.getUsername()))) {
                temp = new HashMap<>();
                temp.put("banner", b);
                temp.put("bannerID", b.getBannerID());
                temp.put("bannerImage", cloudStorageService.getBannerImg(b.getBannerImage()));
                temp.put("bannerStartDate", b.getBannerStartDate());
                temp.put("bannerEndDate", b.getBannerEndDate());
                data.add(temp);
            }
        }

        model.addAttribute("banners", data);
        model.addAttribute("selectedNav", "myCaterer");
        model.addAttribute("selectedPage", "catererbanners");
        return "CatererPage/Banners/catererbanners";
    }

    @GetMapping("/myCaterer/banners/addPage")
    public String addBannerPage(ModelMap model) {
        model.addAttribute("selectedNav", "myCaterer");
        model.addAttribute("selectedPage", "catererbanners");
        return "CatererPage/Banners/addbanner";
    }

    @PostMapping("/add")
    public String addBanner(
            @RequestParam("bannerImage") MultipartFile bannerImage,
            @RequestParam("enddate") int endDays,
            @RequestParam("typeID") Integer typeID,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        Banner banner = new Banner();
        Date currentDate = new Date(); // Ngày hiện tại
        banner.setBannerStartDate(currentDate);

        // Tính toán ngày kết thúc
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, endDays);
        Date bannerEndDate = calendar.getTime();
        banner.setBannerEndDate(bannerEndDate);

        // Lấy đối tượng BannerType từ cơ sở dữ liệu
        BannerType bannerType = bannerTypeService.findById(typeID);
        if (bannerType != null) {
            banner.setTypeID(bannerType);
        } else {
            throw new IllegalArgumentException("Invalid Banner Type ID");
        }

        banner.setCatererEmail(catererService.findById(user.getUsername()));
        if (bannerImage != null && !bannerImage.isEmpty()) {
            String fileName = cloudStorageService.generateFileName(bannerImage, user.getUsername());
            cloudStorageService.uploadFile("banner/" + fileName, bannerImage);
            banner.setBannerImage(fileName);
        }

        bannerManageService.save(banner);
        return "redirect:/caterer/myCaterer/banners";
    }

    @GetMapping("/myCaterer/banners/editPage")
    public String editBannerPage(@RequestParam("bannerID") int bannerID, Model model) {
        Banner banner = bannerManageService.findById(bannerID);
        if (banner == null) {
            // Xử lý khi không tìm thấy banner
        } else {
            // Truyền thông tin banner vào model để hiển thị trên form chỉnh sửa
            model.addAttribute("banner", banner);
        }
        model.addAttribute("bannerImageUrl", cloudStorageService.getBannerImg(banner.getBannerImage()));
        model.addAttribute("selectedNav", "myCaterer");
        model.addAttribute("selectedPage", "catererbanners");
        return "CatererPage/Banners/editbanner";

    }

    @PostMapping("/updateBanner")
    public String updateBanner(
            @RequestParam("bannerID") int bannerID,
            @RequestParam("bannerImage") MultipartFile bannerImage,
            @RequestParam("enddate") int endDays,
            @RequestParam("typeID") Integer typeID,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        Banner banner = bannerManageService.findById(bannerID);
        if (banner == null) {
            redirectAttributes.addFlashAttribute("message", "Banner not found!");
            return "redirect:/caterer/myCaterer/banners";
        }
        // Thực hiện việc cập nhật thông tin của banner từ các tham số
        Date currentDate = new Date();
        banner.setBannerStartDate(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, endDays);
        Date bannerEndDate = calendar.getTime();
        banner.setBannerEndDate(bannerEndDate);
        BannerType bannerType = bannerTypeService.findById(typeID);
        if (bannerType != null) {
            banner.setTypeID(bannerType);
        } else {
            throw new IllegalArgumentException("Invalid Banner Type ID");
        }
        if (bannerImage != null && !bannerImage.isEmpty()) {
            String fileName = cloudStorageService.generateFileName(bannerImage, user.getUsername());
            cloudStorageService.uploadFile("banner/" + fileName, bannerImage);
            banner.setBannerImage(fileName);
        }
        bannerManageService.save(banner);
        return "redirect:/caterer/myCaterer/banners";
    }

    @GetMapping("/deleteBanner")
    public String deleteBanner(@RequestParam("ID") int ID, RedirectAttributes redirectAttributes) {
        Banner banner = bannerManageService.findById(ID);

        if (banner == null) {
            redirectAttributes.addFlashAttribute("message", "Banner not found!");
            return "redirect:/caterer/myCaterer/banners";
        }

        if (bannerManageService.isBannerActive(ID)) {
            redirectAttributes.addFlashAttribute("message", "Active banners cannot be deleted!");
            return "redirect:/caterer/myCaterer/banners";
        }
        cloudStorageService.deleteFile("banner/" + banner.getBannerImage());
        bannerManageService.delete(banner);
        return "redirect:/caterer/myCaterer/banners";
    }
}