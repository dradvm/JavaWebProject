/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Banner;
import com.JavaWebProject.JavaWebProject.models.BannerType;
import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.Dish;
import com.JavaWebProject.JavaWebProject.services.BannerManageService;
import com.JavaWebProject.JavaWebProject.services.BannerTypeService;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CloudStorageService;
import com.JavaWebProject.JavaWebProject.services.DishService;
import com.JavaWebProject.JavaWebProject.services.OrderDetailsService;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        if (user != null) {
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
    public String editDishPage(@RequestParam("ID") int dishID, ModelMap model) {
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

    public void addAndUpdateDish(Dish dish, String dishName, MultipartFile dishImage, String dishDescription, float dishPrice) {
        dish.setCatererEmail(catererService.findById(user.getUsername()));
        dish.setDishDescription(dishDescription);
        dish.setDishName(dishName);
        dish.setDishPrice(dishPrice);
        dish.setDishStatus(1);
        if (dishImage != null) {
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
            @RequestParam("dishPrice") float dishPrice,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Dish dish = new Dish();

        if (dishService.isReachMaxDish(catererService.findById(user.getUsername()))) {
            redirectAttributes.addFlashAttribute("message", "You have reached max dish!!");
        } else {
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
            @RequestParam("dishPrice") float dishPrice,
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
        } else {
            cloudStorageService.deleteFile("dish/" + dish.getDishImage());
            dishService.delete(dish);
        }
        return "redirect:/caterer/myCaterer/dishes";
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
