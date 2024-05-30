/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Dish;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CloudStorageService;
import com.JavaWebProject.JavaWebProject.services.DishService;
import com.JavaWebProject.JavaWebProject.services.OrderDetailsService;
import java.util.ArrayList;
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
        model.addAttribute("dish", dishService.findByDishID(dishID));
        
        return addAndUpdateDishPage();
    }
    
    @GetMapping("/myCaterer/dishes/addAndUpdateDishPage")
    public String addAndUpdateDishPage() {
        return "CatererPage/DishesPage/catererdishesaddupdate";
    }
    
    public void addAndUpdateDish(Dish dish, String dishName, MultipartFile dishImage, String dishDescription, float dishPrice ) {
        dish.setCatererEmail(catererService.findById(user.getUsername()));
        dish.setDishDescription(dishDescription);
        dish.setDishName(dishName);
        dish.setDishPrice(dishPrice);
        dish.setDishStatus(1);
        String fileName = cloudStorageService.generateFileName(dishImage, user.getUsername());
        dish.setDishImage(fileName);
        cloudStorageService.uploadFile("dish/" + fileName, dishImage);
        dishService.save(dish);
    }
    
    @PostMapping("/addDish")
    public String addDish(
            @RequestParam("dishName") String dishName,
            @RequestParam("dishImage") MultipartFile dishImage,
            @RequestParam("dishDescription") String dishDescription,
            @RequestParam("dishPrice") float dishPrice,
            Model model
    ) {
        Dish dish = new Dish();
        addAndUpdateDish(dish, dishName, dishImage, dishDescription, dishPrice);
        
        return "redirect:/caterer/myCaterer/dishes";
    }
    @PostMapping("/updateDish")
    public String addDish(
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
        }
        else {
            cloudStorageService.deleteFile("dish/" + dish.getDishImage());
            dishService.delete(dish);
        }
        return "redirect:/caterer/myCaterer/dishes";
    }
}
