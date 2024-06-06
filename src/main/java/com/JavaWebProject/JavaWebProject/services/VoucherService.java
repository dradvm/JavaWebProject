package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.Voucher;
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
    
    public int countActiveVoucherByDay(LocalDate date) {
        return voucherRepository.countActiveVoucherByDay(date);
    }
    
    public int getActiveVoucherGapByDay(LocalDate date) {
        return countActiveVoucherByDay(date) - countActiveVoucherByDay(date.minusDays(1));
    }
    public ArrayList<Voucher> findAllVoucherAvailable(Caterer caterer) {
        ArrayList<Voucher> data = new ArrayList<Voucher>();
        LocalDate today = LocalDate.now();
        for (Voucher v : voucherRepository.findAllByCatererEmail(caterer)) {
            if (today.isAfter(v.getEndDate()) || today.isBefore(v.getStartDate())) {
                
            }
            else {
                data.add(v);
            }
            
        }
        return data;
    }
    
    public Voucher findById(int id) {
        Optional<Voucher> result = voucherRepository.findById(id);
        return result.isPresent() ? result.get() : null;
    }
}
