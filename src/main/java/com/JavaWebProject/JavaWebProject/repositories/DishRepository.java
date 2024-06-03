/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.Dish;
import java.util.List;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author DELL
 */
public interface DishRepository extends CrudRepository<Dish, Integer> {
    List<Dish> findByCatererEmailAndDishStatus(Caterer caterer, int dishStatus);
    
    Dish findByDishID(Integer dishID);
    
    int countByCatererEmailAndDishStatus(Caterer caterer, int dishStatus);
}
