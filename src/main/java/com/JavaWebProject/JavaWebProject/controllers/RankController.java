package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.CatererRank;
import com.JavaWebProject.JavaWebProject.services.CatererRankService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/rank")
public class RankController {
    @Autowired
    private HttpSession session;
    @Autowired
    private CatererRankService catererRankService;
    
    @RequestMapping(value = "/toBuyrankSignup", method = RequestMethod.GET)
    public String toBuyrank(ModelMap model) {
        AuthController authController = (AuthController) session.getAttribute("scopedTarget.authController");
        if (authController == null || authController.getNewCaterer() == null || authController.getCode() != -1) {
            return "redirect:/";
        }
        model.addAttribute("command", "signup");
        model.addAttribute("catererRankList", catererRankService.findAll());
        return "/RankPage/buyrank";
    }
    
    @RequestMapping(value = "/verifyBuyOptionSignup", method = RequestMethod.POST)
    @ResponseBody
    public String verifyBuyOption(@RequestParam("id") int id) {
        CatererRank rank = catererRankService.findById(id);
        if (rank != null) {
            AuthController authController = (AuthController) session.getAttribute("scopedTarget.authController");
            authController.getNewCaterer().setRankID(rank);
            return "OK";
        }
        else {
            return "Fail";
        }
    }
}
