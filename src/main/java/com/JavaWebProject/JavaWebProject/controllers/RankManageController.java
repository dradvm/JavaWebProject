package com.JavaWebProject.JavaWebProject.controllers;


import com.JavaWebProject.JavaWebProject.models.CatererRank;
import com.JavaWebProject.JavaWebProject.services.RankManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RankManageController {

//    @Autowired
//    private RankManageService rankManageService;
//
//    @GetMapping("/admin/toCatererRanks")
//    public String getCatererRanks(Model model) {
//        model.addAttribute("catererRankList", rankManageService.findAll());
//        return "AdminPage/CatererRank/catererRanks";
//    }
//
//    @GetMapping("/admin/editCatererRank/{id}")
//    public String showEditForm(@PathVariable Integer id, Model model) {
//        CatererRank catererRank = rankManageService.findById(id);
//        model.addAttribute("catererRank", catererRank);
//        return "AdminPage/CatererRank/editCatererRank";
//    }
//
//    @GetMapping("/admin/updateCatererRank/{id}")
//    public String updateCatererRank(@PathVariable Integer id, @ModelAttribute CatererRank catererRank) {
//        catererRank.setRankID(id);
//        rankManageService.save(catererRank);
//        return "redirect:/admin/toCatererRanks";
//    }
//
//    @GetMapping("/admin/deleteCatererRank/{id}")
//    public String deleteCatererRank(@PathVariable Integer id) {
//        rankManageService.deleteById(id);
//        return "redirect:/admin/toCatererRanks";
//    }
//
//    @GetMapping("/admin/addCatererRank")
//    public String addCatererRank(@ModelAttribute CatererRank catererRank) {
//        rankManageService.save(catererRank);
//        return "redirect:/admin/toCatererRanks";
//    }

    

}
