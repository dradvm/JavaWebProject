package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.CateringOrder;
import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.models.Voucher;
import com.JavaWebProject.JavaWebProject.repositories.CateringOrderRepository;
import com.JavaWebProject.JavaWebProject.repositories.VoucherRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private CateringOrderRepository cateringOrderRepository;
    
    public int countActiveVoucherByDay(LocalDate date) {
        return voucherRepository.countActiveVoucherByDay(date);
    }
    
    public int getActiveVoucherGapByDay(LocalDate date) {
        return countActiveVoucherByDay(date) - countActiveVoucherByDay(date.minusDays(1));
    }
    public ArrayList<Voucher> findAllVoucherAvailable(Caterer caterer, Customer customer) {
        ArrayList<Voucher> data = new ArrayList<Voucher>();
        LocalDate today = LocalDate.now();
        if (customer == null) {
            return data;
        }
        for (Voucher v : voucherRepository.findAllByCatererEmail(caterer)) {
            if (today.isAfter(v.getEndDate()) || today.isBefore(v.getStartDate())) {
                
            }
            else {
                if (cateringOrderRepository.findAllByVoucherIDAndCustomerEmail(v, customer).size() == 0) { 
                    data.add(v);
                }
                else {
                    int total = 0;
                    int count = 0;
                    for (CateringOrder co : cateringOrderRepository.findAllByVoucherIDAndCustomerEmail(v, customer)) {
                        count++;
                        if (co.getOrderState().equals("Cancelled")) {
                            total++;
                        }
                    }
                    if (total == count) {
                        data.add(v);
                    }
                }
            }
            
        }
        return data;
    }
    
    public Voucher findById(int id) {
        Optional<Voucher> result = voucherRepository.findById(id);
        return result.isPresent() ? result.get() : null;
    }
}
