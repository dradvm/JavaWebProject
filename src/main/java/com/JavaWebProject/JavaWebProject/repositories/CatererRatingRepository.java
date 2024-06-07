/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.CatererRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author DELL
 */
public interface CatererRatingRepository extends CrudRepository<CatererRating, String> {

    public void save(int catererRating);
    
//    @Query("SELECT cr FROM CatererRating cr WHERE cr.orderID = :orderID")
//    CatererRating findByOrderID(int orderID);

}
