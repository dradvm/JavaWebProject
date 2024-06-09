/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.CatererRank;
import com.JavaWebProject.JavaWebProject.repositories.CatererRepository;
import com.JavaWebProject.JavaWebProject.repositories.RankManageRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankManageService {

    @Autowired
    private CatererRepository catererRepository;
    @Autowired
    private RankManageRepository rankManageRepository;

    public List<CatererRank> findAll() {
        return rankManageRepository.findAll();
    }

    public CatererRank findById(Integer id) {
        return rankManageRepository.findById(id).orElse(null);
    }

    public void save(CatererRank catererRank) {
        rankManageRepository.save(catererRank);
    }

    public void deleteById(Integer id) {
        rankManageRepository.deleteById(id);
    }

    

    public double averageRankFee() {
        return rankManageRepository.averageRankFee();
    }

    public double maxRankFee() {
        return rankManageRepository.maxRankFee();
    }

    public double minRankFee() {
        return rankManageRepository.minRankFee();
    }

    public double averageRankCPO() {
        return rankManageRepository.averageRankCPO();
    }

    public double maxRankCPO() {
        return rankManageRepository.maxRankCPO();
    }

    public double minRankCPO() {
        return rankManageRepository.minRankCPO();
    }

    public int averageRankMaxDish() {
        return rankManageRepository.averageRankMaxDish();
    }

    public int maxRankMaxDish() {
        return rankManageRepository.maxRankMaxDish();
    }

    public int minRankMaxDish() {
        return rankManageRepository.minRankMaxDish();
    }
    public int getTotalRankCount() {
        return rankManageRepository.countAllRanks();
    }

}
