/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.PaymentHistory;
import com.JavaWebProject.JavaWebProject.models.PaymentType;
import com.JavaWebProject.JavaWebProject.repositories.PaymentHistoryRepository;
import com.JavaWebProject.JavaWebProject.repositories.PaymentTypeRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
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
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class PaymentService {
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;
    
    public void savePaymentHistory(PaymentHistory paymentHistory) {
        paymentHistoryRepository.save(paymentHistory);
    }
    
    public PaymentType findPaymentTypeById(int id) {
        return paymentTypeRepository.findById(id);
    }
    
    public ArrayList<Float> getValueByDay(LocalDate date) {
        List<PaymentType> listLabels = paymentTypeRepository.findAll();
        ArrayList<Float> returnData = new ArrayList<>();
        LocalDateTime startDate = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.of(date, LocalTime.MAX);
        for (PaymentType label: listLabels) {
            List<PaymentHistory> listPaymentByLabel = paymentHistoryRepository.findByTypeIDAndPaymentTimeBetween(label, startDate, endDate);
            float sum = 0;
            for (PaymentHistory paymentHistory: listPaymentByLabel) {
                sum+= paymentHistory.getValue();
            }
            returnData.add(sum);
        }
        return returnData;
    }
    public ArrayList<String> getLabelsList() {
        ArrayList<String> listLabels = paymentTypeRepository.findAll().stream().map(PaymentType::getDescription).collect(Collectors.toCollection(ArrayList::new));
        return listLabels;
    }
    
    public List<Map<String, Object>> getValueBarChartInRange(ArrayList listDateLabels) {
        List<Map<String, Object>> datasets = new ArrayList<>();
        List<PaymentType> listLabels = paymentTypeRepository.findAll();
        for (PaymentType paymentType : listLabels) {
            Map<String, Object> set = new HashMap<>();
            ArrayList<Float> data = new ArrayList<>();
            for (Object valueDate: listDateLabels) {
                LocalDateTime startDate;
                LocalDateTime endDate;
                if (valueDate instanceof LocalDate) {
                    startDate = LocalDateTime.of((LocalDate) valueDate, LocalTime.MIN);
                    endDate = LocalDateTime.of((LocalDate) valueDate, LocalTime.MAX);
                }
                else if (valueDate instanceof Month) {
                    int year = LocalDate.now().getYear();
                    int value = ((Month) valueDate).getValue();
                    if (value > LocalDate.now().getMonthValue()) {
                        year--;
                    }
                    startDate = LocalDateTime.of(LocalDate.of(year, value, 1), LocalTime.MIN);
                    endDate = LocalDateTime.of(YearMonth.of(year, value).atEndOfMonth(), LocalTime.MAX);
                }
                else {
                    int value = (int) valueDate;
                    startDate = LocalDateTime.of(LocalDate.of(value, 1, 1), LocalTime.MIN);
                    endDate = LocalDateTime.of(LocalDate.of(value, 12, 31), LocalTime.MAX);
                }
                float sum = 0;
                List<PaymentHistory> paymentList = paymentHistoryRepository.findByTypeIDAndPaymentTimeBetween(paymentType, startDate, endDate);
                for (PaymentHistory paymentHistory : paymentList) {
                    sum+=paymentHistory.getValue();
                }
                data.add(sum);
            }
            set.put("label", paymentType.getDescription());
            set.put("data", data);
            datasets.add(set);
        
        }
        return datasets;
    }
    
    
    public float getTotalValueByDay(LocalDate date) {
        float sum = 0;
        for (Float item : getValueByDay(date)) {
            sum+=item;
        }
        return sum;
    }
    public float getGapPercentRevenueByDay(LocalDate date) {
        float currentValue = getTotalValueByDay(date);
        float oldValue = getTotalValueByDay(date.minusDays(1));
        if (oldValue == 0) {
            if (currentValue == 0) {
                return 0;
            }
            return 100;
        }
        return ((currentValue/oldValue) - 1) * 100;
    }
    
    public int getNewOrderByDay(LocalDate date) {
        return paymentHistoryRepository.countByTypeIDAndPaymentTimeBetween( paymentTypeRepository.findById(3) ,LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));

    }
    public float getGapPercentOrderByDay(LocalDate date) {
       float currentValue = getNewOrderByDay(date);
       float oldValue = getNewOrderByDay(date.minusDays(1));
        if (oldValue == 0) {
            if (currentValue == 0) {
                return 0;
            }
            return 100;
        }
       return ((currentValue/oldValue) - 1) * 100;
    }
    
    public String getPaymentUrl(long amount, String returnURL, HttpServletRequest request) throws ServletException, IOException {
        String secretKey = "ZLEFDKQSO90VAJZNJWEDH6JAKXSEBJJO";
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String bankCode = "NCB";
        String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String vnp_TxnRef = getRandomNumber(8);
        long apiAmount = amount * 100;
        String vnp_IpAddr = getIpAddress(request);
        String vnp_TmnCode = "WCV67Y2G";
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(apiAmount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", returnURL);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = hmacSHA512(secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }
    
    public boolean check(HttpServletRequest request) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            try {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }   
            }
            catch (Exception e) {
                return false;
            }
        }
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    
    private String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }
    
    private String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }
    
    private String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    private String hashAllFields(Map fields) {
        List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(fieldValue);
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        return hmacSHA512("ZLEFDKQSO90VAJZNJWEDH6JAKXSEBJJO",sb.toString());
    }
}
