/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.CatererRating;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author DELL
 */
public interface CatererRatingRepository extends CrudRepository<CatererRating, Integer> {

    public void save(int catererRating);
    
    @Query("SELECT cr FROM CatererRating cr WHERE cr.orderID.orderID = :orderID")
    CatererRating findByOrderID(int orderID);

//    @Query("SELECT cr FROM CatererRating cr WHERE cr.catererEmail.catereEmail = :catererEmail")
    List<CatererRating> findByCatererEmail(Caterer catererEmail);
    
    
    
}
