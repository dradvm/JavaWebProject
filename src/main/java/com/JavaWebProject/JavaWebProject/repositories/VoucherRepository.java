package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.Voucher;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VoucherRepository extends CrudRepository<Voucher, Integer> {
    
    @Query("select count(u) from Voucher u where u.startDate <= ?1 and u.endDate >= ?1")
    int countActiveVoucherByDay(LocalDate date);
}
