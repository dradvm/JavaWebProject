package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.repositories.VoucherRepository;
import java.time.LocalDate;
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
}
