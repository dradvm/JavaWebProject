/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Feedback;
import com.JavaWebProject.JavaWebProject.repositories.FeedbackRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public void save(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    public Feedback findById(int id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public void deleteById(int id) {
        feedbackRepository.deleteById(id);
    }

}
