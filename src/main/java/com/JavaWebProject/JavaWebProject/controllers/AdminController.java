/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.controllers;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.CatererRank;
import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.models.District;
import com.JavaWebProject.JavaWebProject.models.Report;
import com.JavaWebProject.JavaWebProject.services.BannerService;
import com.JavaWebProject.JavaWebProject.services.CatererRankService;
import com.JavaWebProject.JavaWebProject.services.CatererService;
import com.JavaWebProject.JavaWebProject.services.CateringOrderService;
import com.JavaWebProject.JavaWebProject.services.CloudStorageService;
import com.JavaWebProject.JavaWebProject.services.CustomerService;
import com.JavaWebProject.JavaWebProject.services.DistrictService;
import com.JavaWebProject.JavaWebProject.services.PaymentService;
import com.JavaWebProject.JavaWebProject.services.RankManageService;
import com.JavaWebProject.JavaWebProject.services.ReportService;
import com.JavaWebProject.JavaWebProject.services.VoucherService;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private CatererService catererService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private CatererRankService catererRankService;
    @Autowired
    private RankManageService rankManageService;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private CloudStorageService cloudStorageService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private CateringOrderService cateringOrderService;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private BannerService bannerService;
    private ArrayList<LocalDate> days;
    private ArrayList<Month> months;
    private ArrayList<Integer> years;
    private ArrayList<String> labelsDay;
    private ArrayList<String> labelsMonth;
    private ArrayList<String> labelsYear;
    private LocalDate today;

    public void setTabAdminPage(ModelMap model, String page, String title) {
        model.addAttribute("selectedPage", page);
        model.addAttribute("title", title);
    }

    @GetMapping(value = "/dashboard")
    public String adminDashboardPage(ModelMap model) {
        days = new ArrayList<>();
        months = new ArrayList<>();
        years = new ArrayList<>();
        today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            days.add(today.minusDays(i));
            months.add(today.getMonth().minus(i));
            years.add(today.getYear() - i);
        }
        labelsDay = days.stream().map(day
                -> String.valueOf(day.getDayOfMonth())
                + "/"
                + String.valueOf(day.getMonthValue())
        ).collect(Collectors.toCollection(ArrayList::new));
        labelsMonth = months.stream().map(month
                -> String.valueOf(month.getValue())
        ).collect(Collectors.toCollection(ArrayList::new));
        labelsYear = years.stream().map(year
                -> String.valueOf(year)
        ).collect(Collectors.toCollection(ArrayList::new));
        model.addAttribute("newCatererToday", catererService.getNewCatererByDay(today));
        model.addAttribute("newCustomerToday", customerService.getNewCustomerByDay(today));
        model.addAttribute("newOrderToday", paymentService.getNewOrderByDay(today));
        model.addAttribute("revenueToday", paymentService.getTotalValueByDay(today));
        model.addAttribute("gapPercentRevenue", paymentService.getGapPercentRevenueByDay(today));
        model.addAttribute("gapPercentOrder", paymentService.getGapPercentOrderByDay(today));
        model.addAttribute("gapPercentCustomer", customerService.getGapPercentCustomerByDay(today));
        model.addAttribute("gapPercentCaterer", catererService.getGapPercentCatererByDay(today));
        setTabAdminPage(model, "admindashboard", "Dashboard");
        return "AdminPage/admindashboard";
    }

    @GetMapping("/toManageinformationCaterer")
    public String toCatererlist(ModelMap model) {
        setTabAdminPage(model, "admincaterer", "Manage Caterer");
        model.addAttribute("catererList", catererService.findAll());
        return "AdminPage/Caterer/manageinformation";
    }

    @GetMapping("/changeCatererActive")
    public String changeCatererActive(@RequestParam("email") String email) {
        Caterer caterer = catererService.findById(email);
        if (caterer.getActive() == 0) {
            caterer.setActive(1);
        } else {
            caterer.setActive(0);
        }
        catererService.save(caterer);
        return "redirect:/admin/toManageinformationCaterer";
    }

    @GetMapping("/toEditinformationCaterer")
    public String toEditinformationCaterer(@RequestParam("email") String email, ModelMap model) {
        setTabAdminPage(model, "admincaterer", "Manage Caterer");
        Caterer caterer = catererService.findById(email);
        model.addAttribute("caterer", caterer);
        model.addAttribute("img", cloudStorageService.getProfileImg("Caterer", caterer.getProfileImage()));
        model.addAttribute("rankList", catererRankService.findAll());
        model.addAttribute("districtList", districtService.findAll());
        return "AdminPage/Caterer/editinformation";
    }

    @PostMapping("/editinformationCaterer")
    @ResponseBody
    public Map<String, Object> editinformationCaterer(
            @RequestParam("email") String email,
            @RequestParam("profileImg") MultipartFile profileImg,
            @RequestParam("password") String password,
            @RequestParam("rankID") int rankID,
            @RequestParam("rankEndDate") String rankEndDate,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("gender") int gender,
            @RequestParam("address") String address,
            @RequestParam("districtID") int districtID,
            @RequestParam("birthday") String birthday,
            @RequestParam("active") int active) {
        Map<String, Object> result = new HashMap();
        Caterer caterer = catererService.findById(email);
        if (caterer == null) {
            result.put("status", "Invalid");
            return result;
        }
        if (!profileImg.isEmpty()) {
            String type = profileImg.getContentType();
            if (type == null || type.equals("application/octet-stream")) {
                result.put("status", "Invalid");
                return result;
            } else if (!type.equals("image/jpeg") && !type.equals("image/png")) {
                result.put("status", "Invalid");
                return result;
            }
            if (profileImg.getSize() > 10000000) {
                result.put("status", "Invalid");
                return result;
            }
        }
        if (password != null && password.trim().length() > 0 && password.trim().length() < 8) {
            result.put("status", "Invalid");
            return result;
        }
        CatererRank rank = catererRankService.findById(rankID);
        if (rank == null) {
            result.put("status", "Invalid");
            return result;
        }
        Date endDate = new Date();
        try {
            String[] endArr = rankEndDate.split("-");
            int year = Integer.parseInt(endArr[0]);
            int month = Integer.parseInt(endArr[1]);
            int day = Integer.parseInt(endArr[2]);
            endDate = new Date(year - 1900, month - 1, day);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "Invalid");
            return result;
        }
        if (name == null || name.trim().length() == 0) {
            result.put("status", "Invalid");
            return result;
        }
        Pattern pattern = Pattern.compile("^(?:[0-9] ?){7,11}$");
        if (phone == null || !pattern.matcher(phone).matches()) {
            result.put("status", "Invalid");
            return result;
        }
        if (gender != 0 && gender != 1) {
            result.put("status", "Invalid");
            return result;
        }
        if (address == null || address.trim().length() == 0) {
            result.put("status", "Invalid");
            return result;
        }
        District district = districtService.findById(districtID);
        if (district == null) {
            result.put("status", "Invalid");
            return result;
        }
        Date parsedBirthday = new Date();
        if (birthday != null && birthday.trim().length() > 0) {
            String[] arr = birthday.split("-");
            try {
                int year = Integer.parseInt(arr[0]);
                int month = Integer.parseInt(arr[1]);
                int day = Integer.parseInt(arr[2]);
                parsedBirthday = new Date(year - 1900, month - 1, day);
            } catch (Exception e) {
                e.printStackTrace();
                result.put("status", "Invalid");
                return result;
            }
        } else {
            parsedBirthday = null;
        }
        if (active != 0 && active != 1) {
            result.put("status", "Invalid");
            return result;
        }
        if (!profileImg.isEmpty()) {
            if (caterer.getProfileImage() != null) {
                if (!cloudStorageService.deleteFile("caterer/" + caterer.getProfileImage())) {
                    result.put("status", "Fail");
                    return result;
                }
            }
            String fileName = cloudStorageService.generateFileName(profileImg);
            if (cloudStorageService.uploadFile("caterer/" + fileName, profileImg)) {
                caterer.setProfileImage(fileName);
            } else {
                result.put("status", "Fail");
                return result;
            }
        }
        if (password != null && password.trim().length() >= 8) {
            caterer.setPassword(hash(password));
        }
        caterer.setRankID(rank);
        caterer.setRankEndDate(endDate);
        caterer.setFullName(name);
        caterer.setPhone(phone);
        caterer.setGender(gender);
        caterer.setAddress(address);
        caterer.setDistrictID(district);
        if (parsedBirthday != null) {
            caterer.setBirthday(parsedBirthday);
        }
        caterer.setActive(active);
        catererService.save(caterer);
        result.put("status", "OK");
        result.put("target", "/admin/toManageinformationCaterer");
        return result;
    }

    @GetMapping("/toHandlereportsCaterer")
    public String toHandlereportsCaterer(ModelMap model) {
        setTabAdminPage(model, "admincaterer", "Manage Caterer");
        model.addAttribute("reportList", reportService.findReportSentByCustomer());
        return "AdminPage/Caterer/handlereports";
    }

    @GetMapping("/reportSuspendCaterer")
    public String reportSuspendCaterer(@RequestParam("id") int id) {
        Report report = reportService.findById(id);
        Caterer caterer = catererService.findById(report.getReportee());
        caterer.setActive(0);
        catererService.save(caterer);
        report.setReportStatus(1);
        reportService.save(report);
        return "redirect:/admin/toHandlereportsCaterer";
    }

    @GetMapping("/reportSkipCaterer")
    public String reportSkipCaterer(@RequestParam("id") int id) {
        Report report = reportService.findById(id);
        report.setReportStatus(1);
        reportService.save(report);
        return "redirect:/admin/toHandlereportsCaterer";
    }

    @GetMapping("/toStatisticalreportCaterer")
    public String toStatisticalreportCaterer(ModelMap model) {
        setTabAdminPage(model, "admincaterer", "Manage Caterer");
        days = new ArrayList<>();
        months = new ArrayList<>();
        years = new ArrayList<>();
        today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            days.add(today.minusDays(i));
            months.add(today.getMonth().minus(i));
            years.add(today.getYear() - i);
        }
        labelsDay = days.stream().map(day
                -> String.valueOf(day.getDayOfMonth())
                + "/"
                + String.valueOf(day.getMonthValue())
        ).collect(Collectors.toCollection(ArrayList::new));
        labelsMonth = months.stream().map(month
                -> String.valueOf(month.getValue())
        ).collect(Collectors.toCollection(ArrayList::new));
        labelsYear = years.stream().map(year
                -> String.valueOf(year)
        ).collect(Collectors.toCollection(ArrayList::new));
        model.addAttribute("numCatererGotOrdered", cateringOrderService.countDistinctCatererEmailByCreateDate(today));
        model.addAttribute("numAcceptedOrder", paymentService.getNewOrderByDay(today));
        model.addAttribute("numAciveBanner", bannerService.countActiveBannerByDay(today));
        model.addAttribute("numActiveVoucher", voucherService.countActiveVoucherByDay(today));
        model.addAttribute("numCatererGotOrderedGap", cateringOrderService.getNumCatererGotOrderedGapByDay(today));
        model.addAttribute("numAcceptedOrderGap", paymentService.getNumAcceptedOrderGapByDay(today));
        model.addAttribute("numActiveBannerGap", bannerService.getActiveBannerGapByDay(today));
        model.addAttribute("numActiveVoucherGap", voucherService.getActiveVoucherGapByDay(today));
        return "/AdminPage/Caterer/statisticalreport";
    }

    @GetMapping("/toManageinformationCustomer")
    public String toCustomerlist(ModelMap model) {
        setTabAdminPage(model, "admincustomer", "Manage Customer");
        model.addAttribute("customerList", customerService.findAll());
        return "/AdminPage/Customer/manageinformation";
    }

    @GetMapping("/changeCustomerActive")
    public String changeCustomerActive(@RequestParam("email") String email) {
        Customer customer = customerService.findById(email);
        if (customer.getActive() == 0) {
            customer.setActive(1);
        } else {
            customer.setActive(0);
        }
        customerService.save(customer);
        return "redirect:/admin/toManageinformationCustomer";
    }

    @GetMapping("/toEditinformationCustomer")
    public String toEditinformationCustomer(@RequestParam("email") String email, ModelMap model) {
        setTabAdminPage(model, "admincustomer", "Manage Customer");
        Customer customer = customerService.findById(email);
        model.addAttribute("customer", customer);
        model.addAttribute("img", cloudStorageService.getProfileImg("Customer", customer.getProfileImage()));
        model.addAttribute("districtList", districtService.findAll());
        return "AdminPage/Customer/editinformation";
    }

    @PostMapping("/editinformationCustomer")
    @ResponseBody
    public Map<String, Object> editinformationCustomer(
            @RequestParam("email") String email,
            @RequestParam("profileImg") MultipartFile profileImg,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("point") int point,
            @RequestParam("rollChance") int rollChance,
            @RequestParam("phone") String phone,
            @RequestParam("gender") int gender,
            @RequestParam("address") String address,
            @RequestParam("districtID") int districtID,
            @RequestParam("birthday") String birthday,
            @RequestParam("active") int active) {
        Map<String, Object> result = new HashMap();
        Customer customer = customerService.findById(email);
        if (customer == null) {
            result.put("status", "Invalid");
            return result;
        }
        if (!profileImg.isEmpty()) {
            String type = profileImg.getContentType();
            if (type == null || type.equals("application/octet-stream")) {
                result.put("status", "Invalid");
                return result;
            } else if (!type.equals("image/jpeg") && !type.equals("image/png")) {
                result.put("status", "Invalid");
                return result;
            }
            if (profileImg.getSize() > 10000000) {
                result.put("status", "Invalid");
                return result;
            }
        }
        if (password != null && password.trim().length() > 0 && password.trim().length() < 8) {
            result.put("status", "Invalid");
            return result;
        }
        if (name == null || name.trim().length() == 0) {
            result.put("status", "Invalid");
            return result;
        }
        if (point < 0) {
            result.put("status", "Invalid");
            return result;
        }
        if (rollChance < 0) {
            result.put("status", "Invalid");
            return result;
        }
        Pattern pattern = Pattern.compile("^(?:[0-9] ?){7,11}$");
        if (phone == null || !pattern.matcher(phone).matches()) {
            result.put("status", "Invalid");
            return result;
        }
        if (gender != 0 && gender != 1) {
            result.put("status", "Invalid");
            return result;
        }
        if (address == null || address.trim().length() == 0) {
            result.put("status", "Invalid");
            return result;
        }
        District district = districtService.findById(districtID);
        if (district == null) {
            result.put("status", "Invalid");
            return result;
        }
        Date parsedBirthday = new Date();
        if (birthday != null && birthday.trim().length() > 0) {
            String[] arr = birthday.split("-");
            try {
                int year = Integer.parseInt(arr[0]);
                int month = Integer.parseInt(arr[1]);
                int day = Integer.parseInt(arr[2]);
                parsedBirthday = new Date(year - 1900, month - 1, day);
            } catch (Exception e) {
                e.printStackTrace();
                result.put("status", "Invalid");
                return result;
            }
        } else {
            parsedBirthday = null;
        }
        if (active != 0 && active != 1) {
            result.put("status", "Invalid");
            return result;
        }
        if (!profileImg.isEmpty()) {
            if (customer.getProfileImage() != null) {
                if (!cloudStorageService.deleteFile("customer/" + customer.getProfileImage())) {
                    result.put("status", "Fail");
                    return result;
                }
            }
            String fileName = cloudStorageService.generateFileName(profileImg);
            if (cloudStorageService.uploadFile("customer/" + fileName, profileImg)) {
                customer.setProfileImage(fileName);
            } else {
                result.put("status", "Fail");
                return result;
            }
        }
        if (password != null && password.trim().length() >= 8) {
            customer.setPassword(hash(password));
        }
        customer.setFullName(name);
        customer.setPoint(point);
        customer.setRollChance(rollChance);
        customer.setPhone(phone);
        customer.setGender(gender);
        customer.setAddress(address);
        customer.setDistrictID(district);
        if (parsedBirthday != null) {
            customer.setBirthday(parsedBirthday);
        }
        customer.setActive(active);
        customerService.save(customer);
        result.put("status", "OK");
        result.put("target", "/admin/toManageinformationCustomer");
        return result;
    }

    @GetMapping("/toHandlereportsCustomer")
    public String toHandlereportsCustomer(ModelMap model) {
        setTabAdminPage(model, "admincustomer", "Manage Customer");
        model.addAttribute("reportList", reportService.findReportSentByCaterer());
        return "AdminPage/Customer/handlereports";
    }

    @GetMapping("/reportSuspendCustomer")
    public String reportSuspendCustomer(@RequestParam("id") int id) {
        Report report = reportService.findById(id);
        Customer customer = customerService.findById(report.getReportee());
        customer.setActive(0);
        customerService.save(customer);
        report.setReportStatus(1);
        reportService.save(report);
        return "redirect:/admin/toHandlereportsCustomer";
    }

    @GetMapping("/reportSkipCustomer")
    public String reportSkipCustomer(@RequestParam("id") int id) {
        Report report = reportService.findById(id);
        report.setReportStatus(1);
        reportService.save(report);
        return "redirect:/admin/toHandlereportsCustomer";
    }

    @GetMapping("/toStatisticalreportCustomer")
    public String toStatisticalreportCustomer(ModelMap model) {
        setTabAdminPage(model, "admincustomer", "Manage Customer");
        days = new ArrayList<>();
        months = new ArrayList<>();
        years = new ArrayList<>();
        today = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            days.add(today.minusDays(i));
            months.add(today.getMonth().minus(i));
            years.add(today.getYear() - i);
        }
        labelsDay = days.stream().map(day
                -> String.valueOf(day.getDayOfMonth())
                + "/"
                + String.valueOf(day.getMonthValue())
        ).collect(Collectors.toCollection(ArrayList::new));
        labelsMonth = months.stream().map(month
                -> String.valueOf(month.getValue())
        ).collect(Collectors.toCollection(ArrayList::new));
        labelsYear = years.stream().map(year
                -> String.valueOf(year)
        ).collect(Collectors.toCollection(ArrayList::new));
        model.addAttribute("numNewCustomer", customerService.getNewCustomerByDay(today));
        model.addAttribute("numOrder", cateringOrderService.countByCreateDate(today));
        model.addAttribute("numCustomerOrdered", cateringOrderService.countDistinctCustomerEmailByCreateDate(today));
        model.addAttribute("pointUsed", cateringOrderService.sumPointDiscountByCreateDate(today));
        model.addAttribute("numNewCustomerGap", customerService.getNewCustomerGapByDay(today));
        model.addAttribute("numOrderGap", cateringOrderService.getNewOrderGapByDay(today));
        model.addAttribute("numCustomerOrderedGap", cateringOrderService.getNumCustomerOrderedGapByDay(today));
        model.addAttribute("pointUsedGap", cateringOrderService.getPointUsedGapByDay(today));
        return "/AdminPage/Customer/statisticalreport";
    }

//    @GetMapping("/manageCatererRank")
//    public String adminCatererRankPage(ModelMap model) {
//        setTabAdminPage(model, "admincatererrank", "Manage Caterer Rank");
//        return "AdminPage/admincatererrank";
//    }
    @GetMapping("/manageFeedback")
    public String adminFeedbackPage(ModelMap model) {
        setTabAdminPage(model, "adminfeedback", "Manage Feedback");
        return "AdminPage/adminfeedback";
    }

    @GetMapping("/lineChart")
    @ResponseBody
    public Map<String, Object> getDataLineChart(@RequestParam("selectedValue") String selectedValue) {
        // Xử lý logic dựa trên selectedValue
        Map<String, Object> data = new HashMap<>();
        ArrayList<Integer> dataChart = new ArrayList<>();
        switch (selectedValue) {
            case "Day":
                data.put("labels", labelsDay);
                for (LocalDate day : days) {
                    dataChart.add(catererService.getNewCatererByDay(day) + customerService.getNewCustomerByDay(day));
                }
                break;
            case "Month":
                data.put("labels", labelsMonth);
                for (Month month : months) {
                    dataChart.add(catererService.getNewCatererByMonth(month) + customerService.getNewCustomerByMonth(month));
                }
                break;
            case "Year":
                data.put("labels", labelsYear);
                for (int year : years) {
                    dataChart.add(catererService.getNewCatererByYear(year) + customerService.getNewCustomerByYear(year));
                }
                break;
            default:
                data.put("labels", new String[]{});
                data.put("data", new int[]{});
                break;
        }
        data.put("data", dataChart);
        return data; // Chỉ trả về fragment cần cập nhật
    }

    @GetMapping("/lineChartCaterer")
    @ResponseBody
    public Map<String, Object> getDataLineChartCaterer(@RequestParam("selectedValue") String selectedValue) {
        Map<String, Object> data = new HashMap<>();
        ArrayList<Integer> dataChart = new ArrayList<>();
        switch (selectedValue) {
            case "Day":
                data.put("labels", labelsDay);
                for (LocalDate day : days) {
                    dataChart.add(paymentService.getNewOrderByDay(day));
                }
                break;
            case "Month":
                data.put("labels", labelsMonth);
                for (Month month : months) {
                    dataChart.add(paymentService.getNewOrderByMonth(month));
                }
                break;
            case "Year":
                data.put("labels", labelsYear);
                for (int year : years) {
                    dataChart.add(paymentService.getNewOrderByYear(year));
                }
                break;
            default:
                data.put("labels", new String[]{});
                data.put("data", new int[]{});
                break;
        }
        data.put("data", dataChart);
        return data;
    }

    @GetMapping("/lineChartCustomer")
    @ResponseBody
    public Map<String, Object> getDataLineChartCustomer(@RequestParam("selectedValue") String selectedValue) {
        Map<String, Object> data = new HashMap<>();
        ArrayList<Integer> dataChart = new ArrayList<>();
        switch (selectedValue) {
            case "Day":
                data.put("labels", labelsDay);
                for (LocalDate day : days) {
                    dataChart.add(customerService.getNewCustomerByDay(day));
                }
                break;
            case "Month":
                data.put("labels", labelsMonth);
                for (Month month : months) {
                    dataChart.add(customerService.getNewCustomerByMonth(month));
                }
                break;
            case "Year":
                data.put("labels", labelsYear);
                for (int year : years) {
                    dataChart.add(customerService.getNewCustomerByYear(year));
                }
                break;
            default:
                data.put("labels", new String[]{});
                data.put("data", new int[]{});
                break;
        }
        data.put("data", dataChart);
        return data;
    }

    @GetMapping("/polarAreaChart")
    @ResponseBody
    Map<String, Object> getDataPolarAreaChart() {
        Map<String, Object> data = new HashMap<>();
        data.put("labels", paymentService.getLabelsList());
        data.put("data", paymentService.getValueByDay(LocalDate.now()).stream().mapToDouble(Float::doubleValue));
        return data;
    }

    @GetMapping("/pieChartCaterer")
    @ResponseBody
    public Map<String, Object> getPieChartCaterer() {
        Map<String, Object> result = new HashMap();
        result.put("labels", bannerService.getAllTypeDescription());
        result.put("data", bannerService.countActiveBannerPerTypeByDay(today));
        return result;
    }

    @GetMapping("/barChart")
    @ResponseBody
    public Map<String, Object> getDataBarChart(@RequestParam("selectedValue") String selectedValue) {
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> datasets = new ArrayList<>();
        switch (selectedValue) {
            case "Day":
                data.put("labels", labelsDay);
                datasets = paymentService.getValueBarChartInRange(days);
                break;
            case "Month":
                data.put("labels", labelsMonth);
                datasets = paymentService.getValueBarChartInRange(months);
                break;
            case "Year":
                data.put("labels", labelsYear);
                datasets = paymentService.getValueBarChartInRange(years);
                break;
            default:
                data.put("labels", new String[]{});
                data.put("datasets", new int[]{});
                break;
        }
        data.put("datasets", datasets);
        return data;
    }

    @GetMapping("/barChartCaterer")
    @ResponseBody
    public Map<String, Object> getBarChartCaterer(@RequestParam("selectedValue") String selectedValue) {
        Map<String, Object> data = new HashMap();
        List<Map<String, Object>> datasets = new ArrayList();
        if (selectedValue.equals("Day")) {
            data.put("labels", labelsDay);
            datasets = paymentService.getValueBarChartCaterer(days);
        } else if (selectedValue.equals("Month")) {
            data.put("labels", labelsMonth);
            datasets = paymentService.getValueBarChartCaterer(months);
        } else {
            data.put("labels", labelsYear);
            datasets = paymentService.getValueBarChartCaterer(years);
        }
        data.put("datasets", datasets);
        return data;
    }

    @GetMapping("/barChartCustomer")
    @ResponseBody
    public Map<String, Object> getBarChartCustomer(@RequestParam("selectedValue") String selectedValue) {
        Map<String, Object> data = new HashMap();
        List<Map<String, Object>> datasets = new ArrayList();
        if (selectedValue.equals("Day")) {
            data.put("labels", labelsDay);
            datasets = paymentService.getValueBarChartCaterer(days);
        } else if (selectedValue.equals("Month")) {
            data.put("labels", labelsMonth);
            datasets = paymentService.getValueBarChartCaterer(months);
        } else {
            data.put("labels", labelsYear);
            datasets = paymentService.getValueBarChartCaterer(years);
        }
        data.put("datasets", datasets);
        return data;
    }

    @GetMapping("/toManageCatererRanks")
    public String toCatererRanklist(ModelMap model) {
        setTabAdminPage(model, "admincatererrank", "Manage Caterer Rank");
        model.addAttribute("catererRankList", rankManageService.findAll());
        return "AdminPage/CatererRank/catererRanks";
    }

    private String hash(String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger number = new BigInteger(1, md.digest(str.getBytes(StandardCharsets.UTF_8)));
        StringBuilder hex = new StringBuilder(number.toString(16));
        while (hex.length() < 64) {
            hex.insert(0, '0');
        }
        return hex.toString();
    }

    @GetMapping("/editCatererRank")
    public String toEditCatererRank(@RequestParam("id") int id, ModelMap model) {
        CatererRank catererRank = rankManageService.findById(id);
        if (catererRank != null) {
            model.addAttribute("catererRank", catererRank);
            return "AdminPage/CatererRank/editCatererRank";
        } else {
            return "redirect:/admin/toManageCatererRanks";
        }
    }

    // Xử lý cập nhật Caterer Rank
    @PostMapping("/editCatererRank")
    public String updateCatererRank(
            @RequestParam("rankID") int rankID,
            @RequestParam("rankFee") double rankFee,
            @RequestParam("rankCPO") double rankCPO,
            @RequestParam("rankMaxDish") int rankMaxDish,
            RedirectAttributes redirectAttributes) {
        CatererRank catererRank = rankManageService.findById(rankID);
        if (catererRank == null) {
            redirectAttributes.addFlashAttribute("status", "Invalid Rank ID");
            return "redirect:/admin/toManageCatererRanks";
        }
        catererRank.setRankFee(rankFee);
        catererRank.setRankCPO(rankCPO);
        catererRank.setRankMaxDish(rankMaxDish);
        rankManageService.save(catererRank);
        redirectAttributes.addFlashAttribute("status", "Success");
        return "redirect:/admin/toManageCatererRanks";
    }

    // Xóa Caterer Rank
    @PostMapping("/deleteCatererRank")
    public String deleteCatererRank(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        if (catererService.existsByRankID(id)) {
            redirectAttributes.addFlashAttribute("status", "Cannot delete: Rank is in use");
            return "redirect:/admin/toManageCatererRanks";
        }

        rankManageService.deleteById(id);
        redirectAttributes.addFlashAttribute("status", "Deleted");
        return "redirect:/admin/toManageCatererRanks";
    }

    @GetMapping("/addCatererRank")
    public String showAddCatererRankForm(ModelMap model) {
        model.addAttribute("catererRank", new CatererRank());
        return "AdminPage/CatererRank/addCatererRank";
    }

    @PostMapping("/addCatererRank")
    public String addCatererRank(
            @RequestParam("rankFee") double rankFee,
            @RequestParam("rankCPO") double rankCPO,
            @RequestParam("rankMaxDish") int rankMaxDish,
            RedirectAttributes redirectAttributes) {

        CatererRank catererRank = new CatererRank();
        catererRank.setRankFee(rankFee);
        catererRank.setRankCPO(rankCPO);
        catererRank.setRankMaxDish(rankMaxDish);

        rankManageService.save(catererRank);

        redirectAttributes.addFlashAttribute("status", "Caterer Rank added successfully");
        return "redirect:/admin/toManageCatererRanks";
    }
    @GetMapping("/toStatisticalReport")
    public String toStatisticalReport(Model model) {
        
        double averageRankFee = rankManageService.averageRankFee();
        double maxRankFee = rankManageService.maxRankFee();
        double minRankFee = rankManageService.minRankFee();
        double averageRankCPO = rankManageService.averageRankCPO();
        double maxRankCPO = rankManageService.maxRankCPO();
        double minRankCPO = rankManageService.minRankCPO();
        int averageRankMaxDish = rankManageService.averageRankMaxDish();
        int maxRankMaxDish = rankManageService.maxRankMaxDish();
        int minRankMaxDish = rankManageService.minRankMaxDish();

        model.addAttribute("averageRankFee", averageRankFee);
        model.addAttribute("maxRankFee", maxRankFee);
        model.addAttribute("minRankFee", minRankFee);
        model.addAttribute("averageRankCPO", averageRankCPO);
        model.addAttribute("maxRankCPO", maxRankCPO);
        model.addAttribute("minRankCPO", minRankCPO);
        model.addAttribute("averageRankMaxDish", averageRankMaxDish);
        model.addAttribute("maxRankMaxDish", maxRankMaxDish);
        model.addAttribute("minRankMaxDish", minRankMaxDish);

        return "AdminPage/CatererRank/rankStatisticalReport";
    }
}
