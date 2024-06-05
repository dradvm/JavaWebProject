//package com.JavaWebProject.JavaWebProject.controllers;
//
//import com.JavaWebProject.JavaWebProject.models.Banner;
//import com.JavaWebProject.JavaWebProject.models.BannerType;
//import com.JavaWebProject.JavaWebProject.services.BannerManageService;
//import com.JavaWebProject.JavaWebProject.services.BannerTypeService;
//import com.JavaWebProject.JavaWebProject.services.CloudStorageService;
//import com.JavaWebProject.JavaWebProject.services.CatererService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.Date;
//
//@Controller
//@RequestMapping("/banner")
//public class BannerController {
//
//    @Autowired
//    private AuthController user;
//
//    @Autowired
//    private BannerManageService bannerManageService;
//
//    @Autowired
//    private CatererService catererService;
//
//    @Autowired
//    private CloudStorageService cloudStorageService;
//
//    @Autowired
//    private BannerTypeService bannerTypeService;
//
//    private void addAndUpdateBanner(Banner banner, MultipartFile bannerImage, Date bannerEndDate, Integer typeID, HttpSession session) {
//        banner.setBannerStartDate(new Date()); // Ngày hiện tại
//        banner.setBannerEndDate(bannerEndDate);
//
//        // Lấy đối tượng BannerType từ cơ sở dữ liệu
//        BannerType bannerType = bannerTypeService.findById(typeID);
//        if (bannerType != null) {
//            banner.setTypeID(bannerType);
//        } else {
//            throw new IllegalArgumentException("Invalid Banner Type ID");
//        }
//
//        banner.setCatererEmail(catererService.findById(user.getUsername()));
//        if (bannerImage != null && !bannerImage.isEmpty()) {
//            String fileName = cloudStorageService.generateFileName(bannerImage, user.getUsername());
//            cloudStorageService.uploadFile("banner/" + fileName, bannerImage);
//            banner.setBannerImage(fileName);
//        }
//
//        bannerManageService.save(banner);
//    }
//
//    @PostMapping("/addBanner")
//    public String addBanner(
//            @RequestParam("bannerImage") MultipartFile bannerImage,
//            @RequestParam("bannerEndDate") Date bannerEndDate,
//            @RequestParam("typeID") Integer typeID,
//            HttpSession session,
//            RedirectAttributes redirectAttributes
//    ) {
//        Banner banner = new Banner();
//        addAndUpdateBanner(banner, bannerImage, bannerEndDate, typeID, session);
//        return "redirect:/banners";
//    }
//
//    @PostMapping("/updateBanner")
//    public String updateBanner(
//            @RequestParam("bannerID") int bannerID,
//            @RequestParam("bannerImage") MultipartFile bannerImage,
//            @RequestParam("bannerEndDate") Date bannerEndDate,
//            @RequestParam("typeID") Integer typeID,
//            HttpSession session,
//            RedirectAttributes redirectAttributes
//    ) {
//        Banner banner = bannerManageService.findById(bannerID);
//
//        if (banner == null) {
//            redirectAttributes.addFlashAttribute("message", "Banner not found!");
//            return "redirect:/banners";
//        }
//
//        addAndUpdateBanner(banner, bannerImage, bannerEndDate, typeID, session);
//        return "redirect:/banners";
//    }
//
//    @GetMapping("/deleteBanner")
//    public String deleteBanner(@RequestParam("ID") int ID, RedirectAttributes redirectAttributes) {
//        Banner banner = bannerManageService.findById(ID);
//
//        if (banner == null) {
//            redirectAttributes.addFlashAttribute("message", "Banner not found!");
//            return "redirect:/banners";
//        }
//
//        if (bannerManageService.isBannerActive(ID)) {
//            redirectAttributes.addFlashAttribute("message", "Active banners cannot be deleted!");
//            return "redirect:/banners";
//        }
//
//        cloudStorageService.deleteFile("banner/" + banner.getBannerImage());
//        bannerManageService.delete(banner);
//        return "redirect:/banners";
//    }
//}
