/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Dish;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CloudStorageService;
import com.JavaWebProject.JavaWebProject.services.DishService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private CloudStorageService cloudStorageService;
    
    @GetMapping(value = "/myCaterer/orders")
    public String orderPage(ModelMap model) {
        model.addAttribute("selectedNav", "myCaterer");
        model.addAttribute("selectedPage", "catererorders");
        return "CatererPage/catererorders";
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
                data.add(temp);
            }
        }
        model.addAttribute("dishes", data);
        model.addAttribute("selectedNav", "myCaterer");
        model.addAttribute("selectedPage", "catererdishes");
        return "CatererPage/catererdishes";
    }
    @GetMapping(value = "/deleteDish")
    public String deleteDish(@RequestParam int ID) {
        Dish dish = dishService.findByDishID(ID);
        dish.setDishStatus(0);
        dishService.save(dish);
        return "redirect:/caterer/myCaterer/dishes";
    }
}
