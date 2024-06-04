package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.CatererRank;
import com.JavaWebProject.JavaWebProject.models.PaymentHistory;
import com.JavaWebProject.JavaWebProject.services.CatererRankService;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
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
    @Autowired
    private CatererService catererService;
    
    @RequestMapping(value = "/toBuyrankSignup", method = RequestMethod.GET)
    public String toBuyrank(ModelMap model) {
        AuthController authController = (AuthController) session.getAttribute("scopedTarget.authController");
        if (authController == null || authController.getNewCaterer() == null || authController.getCode() != -1) {
            return "redirect:/";
        }
        model.addAttribute("command", "signup");
        model.addAttribute("catererRankList", catererRankService.findAll());
        return "RankPage/buyrank";
    }
    
    @RequestMapping(value = "/verifyBuyOptionSignup", method = RequestMethod.POST)
    @ResponseBody
    public String verifyBuyOptionSignup(@RequestParam("id") int id) {
        CatererRank rank = catererRankService.findById(id);
        if (rank != null) {
            AuthController authController = (AuthController) session.getAttribute("scopedTarget.authController");
            authController.getNewCaterer().setRankID(rank);
            try {
                return paymentService.getPaymentUrl((long) (rank.getRankFee() * 25000), "http://localhost:8080/rank/responseSignup", request);
            }
            catch (Exception e) {
                return "Fail";
            }
        }
        else {
            return "Fail";
        }
    }
    
    @RequestMapping(value = "/responseSignup", method = RequestMethod.GET)
    public String responseSignup() {
        if (paymentService.check(request)) {
            return "redirect:/auth/completeSignupCaterer";
        }
        else {
            return "RankPage/paymenterror";
        }
    }
    
    @RequestMapping(value = "/toBuyrankBuy", method = RequestMethod.GET)
    public String toBuyrankBuy(ModelMap model) {
        AuthController authController = (AuthController) session.getAttribute("scopedTarget.authController");
        if (authController == null || authController.getRole() == null || !authController.getRole().equals("Caterer")) {
            return "redirect:/";
        }
        model.addAttribute("command", "buy");
        model.addAttribute("catererRankList", catererRankService.findAll());
        return "RankPage/buyrank";
    }
    
    @RequestMapping(value = "/verifyBuyOptionBuy", method = RequestMethod.POST)
    @ResponseBody
    public String verifyBuyOptionBuy(@RequestParam("id") int id) {
        CatererRank rank = catererRankService.findById(id);
        if (rank != null) {
            try {
                AuthController authController = (AuthController) session.getAttribute("scopedTarget.authController");
                Caterer caterer = catererService.findById(authController.getUsername());
                int catererPoint = caterer.getPoint() != null ? (int) caterer.getPoint() : 0;
                double discount = catererPoint > rank.getRankFee() ? rank.getRankFee() : catererPoint;
                long fee = (long) ((rank.getRankFee() - discount) * 25000);
                if (caterer.getRankID().getRankID() == id) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(caterer.getRankEndDate());
                    calendar.add(Calendar.DAY_OF_MONTH, 30);
                    Date endDate = calendar.getTime();
                    caterer.setRankEndDate(endDate);
                }
                else {
                    Date start = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(start);
                    calendar.add(Calendar.DAY_OF_MONTH, 30);
                    Date end = calendar.getTime();
                    caterer.setRankID(rank);
                    caterer.setRankStartDate(start);
                    caterer.setRankEndDate(end);
                }
                if (fee == 0) {
                    PaymentHistory payment = new PaymentHistory();
                    payment.setCatererEmail(caterer);
                    int point = caterer.getPoint();
                    payment.setValue(0);
                    payment.setDescription("Rank buy " + rank.getRankID());
                    payment.setTypeID(paymentService.findPaymentTypeById(1));
                    payment.setPaymentTime(new Date());
                    caterer.setPoint((int) (point - discount));
                    catererService.save(caterer);
                    paymentService.savePaymentHistory(payment);
                    return "/auth/toProfile";
                }
                authController.setNewCaterer(caterer);
                String returnURL = "http://localhost:8080/rank/responseBuy";
                return paymentService.getPaymentUrl(fee, returnURL, request);
            }
            catch (Exception e) {
                return "Fail";
            }
        }
        else {
            return "Fail";
        }
    }
    
    @RequestMapping(value = "/responseBuy", method = RequestMethod.GET)
    public String responseBuy() {
        if (paymentService.check(request)) {
            AuthController authController = (AuthController) session.getAttribute("scopedTarget.authController");
            Caterer caterer = authController.getNewCaterer();
            PaymentHistory payment = new PaymentHistory();
            payment.setCatererEmail(caterer);
            CatererRank rank = caterer.getRankID();
            int point = caterer.getPoint();
            double discount = point > rank.getRankFee() ? rank.getRankFee() : point;
            payment.setValue(rank.getRankFee() - discount);
            payment.setDescription("Rank buy " + rank.getRankID());
            payment.setTypeID(paymentService.findPaymentTypeById(1));
            payment.setPaymentTime(new Date());
            caterer.setPoint((int) (point - discount));
            catererService.save(caterer);
            paymentService.savePaymentHistory(payment);
            authController.setNewCaterer(null);
            return "redirect:/auth/toProfile";
        }
        else {
            return "RankPage/paymenterror";
        }
    }
}
