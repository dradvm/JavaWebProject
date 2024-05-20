/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.MinigameReward;
import com.JavaWebProject.JavaWebProject.repositories.MinigameRewardRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class MinigameRewardService {
    @Autowired
    private MinigameRewardRepository minigameRewardRepository;
    
    public List<MinigameReward> getAllMinigameReward() {
        return minigameRewardRepository.findAll();
    }
}
