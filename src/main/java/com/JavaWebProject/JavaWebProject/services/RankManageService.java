/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.CatererRank;
import com.JavaWebProject.JavaWebProject.repositories.RankManageRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankManageService {

    @Autowired
    private RankManageRepository rankManageRepository;

    public List<CatererRank> findAll() {
        return rankManageRepository.findAll();
    }

    public CatererRank findById(Integer id) { // Changed to Integer
        return rankManageRepository.findById(id).orElse(null);
    }

    public void save(CatererRank catererRank) {
        rankManageRepository.save(catererRank);
    }

    public void deleteById(Integer id) { // Changed to Integer
        rankManageRepository.deleteById(id);
    }
}
