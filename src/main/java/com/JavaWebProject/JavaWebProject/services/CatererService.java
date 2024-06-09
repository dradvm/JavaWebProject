/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.controllers.AuthController;
import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.repositories.CatererRepository;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class CatererService {

    @Autowired
    private CatererRepository catererRepository;
    @Autowired
    private AuthController authController;

    public Caterer findById(String catererEmail) {
        Optional<Caterer> result = catererRepository.findById(catererEmail);
        return result.isPresent() ? result.get() : null;
    }

    public List<Caterer> findAll() {
        return catererRepository.findAll();
    }

    public List<Caterer> findAllCatererActive() {
        ArrayList<Caterer> data = new ArrayList<Caterer>();
        LocalDate today = LocalDate.now();
        Date t = new Date(today.getYear() - 1900, today.getMonthValue() - 1, today.getDayOfMonth());
        for (Caterer c : catererRepository.findByActive(1)) {
            if (!t.before(c.getRankStartDate()) && !t.after(c.getRankEndDate())) {
                if (c.getDishCollection().size() > 0) {
                    data.add(c);
                }
            }
        }
        return data;
    }

    public Caterer findByCatererEmailAndFullName(String fullName, String catererEmail) {
        List<Caterer> caterers = catererRepository.findByFullName(fullName);
        Caterer caterer = caterers.get(0);
        for (Caterer c : caterers) {
            if (authController.hash_public(c.getCatererEmail()).equals(catererEmail)) {
                caterer = c;
                break;
            }
        }
        return caterer;
    }

    public int getNewCatererByDay(LocalDate date) {
        return catererRepository.countByCreateDate(date);
    }

    public float getGapPercentCatererByDay(LocalDate date) {
        float currentValue = getNewCatererByDay(date);
        float oldValue = getNewCatererByDay(date.minusDays(1));
        if (oldValue == 0) {
            if (currentValue == 0) {
                return 0;
            }
            return 100;
        }
        return ((currentValue / oldValue) - 1) * 100;
    }

    public int getNewCatererByMonth(Month month) {
        int year = LocalDate.now().getYear();
        if (month.getValue() > LocalDate.now().getMonthValue()) {
            year--;
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();
        return catererRepository.countByCreateDateBetween(startDate, endDate);
    }

    public int getNewCatererByYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return catererRepository.countByCreateDateBetween(startDate, endDate);
    }

    public void save(Caterer caterer) {
        catererRepository.save(caterer);
    }

    public boolean existsByRankID(int rankID) {
        return catererRepository.existsByRankID_RankID(rankID);
    }

    public boolean existsByCatererEmail (String catererEmail) {
        return catererRepository.existsByCatererEmail (catererEmail);
    }
    public Map<Integer, Integer> getCountsForRanks() {
        int countRank1 = catererRepository.countByRankID_RankID(1);
        int countRank2 = catererRepository.countByRankID_RankID(2);
        int countRank3 = catererRepository.countByRankID_RankID(3);

        Map<Integer, Integer> rankCounts = new HashMap<>();
        rankCounts.put(1, countRank1);
        rankCounts.put(2, countRank2);
        rankCounts.put(3, countRank3);

        return rankCounts;
    }
    }
