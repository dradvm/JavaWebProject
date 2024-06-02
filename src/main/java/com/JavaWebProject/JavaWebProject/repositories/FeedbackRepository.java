/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.Feedback;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author DELL
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    public int countByFeedbackDateBetween(LocalDate startDate, LocalDate endDate);

    public int countByFeedbackDate(LocalDate date);  

    public List<Feedback> findByFeedbackDate(LocalDate feedbackDate);

}

