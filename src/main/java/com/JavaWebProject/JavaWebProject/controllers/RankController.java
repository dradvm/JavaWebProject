package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.CatererRank;
import com.JavaWebProject.JavaWebProject.services.CatererRankService;
import com.JavaWebProject.JavaWebProject.services.PaymentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
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
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private PaymentService paymentService;
    
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
            try {
                return paymentService.getPaymentUrl((long) (rank.getRankFee() * 25000), "http://localhost:8080/rank/response", request);
            }
            catch (Exception e) {
                return "Fail";
            }
        }
        else {
            return "Fail";
        }
    }
    
    @RequestMapping(value = "/response", method = RequestMethod.GET)
    public String response() {
        if (paymentService.check(request)) {
            return "redirect:/auth/completeSignupCaterer";
        }
        else {
            return "/RankPage/paymenterror";
        }
    }
}
