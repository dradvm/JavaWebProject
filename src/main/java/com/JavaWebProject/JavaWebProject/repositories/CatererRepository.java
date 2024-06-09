/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author DELL
 */
public interface CatererRepository extends CrudRepository<Caterer, String> {
    
    List<Caterer> findAll();
    
    List<Caterer> findByFullName(String fullName);
    
    List<Caterer> findByActive(int active);
    
    int countByCreateDate(LocalDate createDate);
    
    int countByCreateDateBetween(LocalDate startDate, LocalDate endDate);

    public boolean existsByRankID_RankID(int rankID);

    public boolean existsByCatererEmail(String catererEmail);

    int countByRankID_RankID(int rankID);

}
