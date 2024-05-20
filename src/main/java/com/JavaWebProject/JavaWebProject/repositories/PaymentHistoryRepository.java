/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.PaymentHistory;
import com.JavaWebProject.JavaWebProject.models.PaymentType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author DELL
 */
public interface PaymentHistoryRepository extends CrudRepository<PaymentHistory, Long> {
    List<PaymentHistory> findByTypeIDAndPaymentTimeBetween(PaymentType paymentType, LocalDateTime startDate, LocalDateTime endDate);
}
