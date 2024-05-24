package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    @Autowired
    private HttpSession session;
    @Autowired
    private CatererService catererService;
    @Autowired
    private CustomerService customerService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(ModelMap model) {
        AuthController auth = (AuthController) session.getAttribute("scopedTarget.authController");
        if (auth != null) {
            String role = auth.getRole();
            model.addAttribute("role", role);
            model.addAttribute("username", auth.getUsername());
            if (role == null) {
                return "index";
            }
            else if (role.equals("Customer")) {
                model.addAttribute("name", customerService.findById(auth.getUsername()).getFullName());
            }
            else if (role.equals("Caterer")) {
                model.addAttribute("name", catererService.findById(auth.getUsername()).getFullName());
            }
        }
        return "index";
    }
}
