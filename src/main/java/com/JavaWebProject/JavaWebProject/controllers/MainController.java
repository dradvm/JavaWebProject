package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    CatererService catererService;
    
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
        for (Caterer c : catererService.findAll()) {
            temp = new HashMap<>();
            temp.put("fullName", c.getFullName().replace(" ", "-"));
            temp.put("catererEmail", c.getCatererEmail());
            temp.put("catererRating", c.getCatererRating());
            temp.put("description", c.getDescription());
            temp.put("image", c.getProfileImage());
            data.add(temp);
        }
        return data;
    }
    @GetMapping("/listCaterer/{fullName}")
    public String detailsCatererPage(@PathVariable("fullName") String fullName) {
           return "CustomerPage/customerdetailscaterer";
    }
}
