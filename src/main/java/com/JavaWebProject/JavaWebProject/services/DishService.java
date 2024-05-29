/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.Dish;
import com.JavaWebProject.JavaWebProject.repositories.DishRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;
    public List<Dish> findAllDishOfCaterer(Caterer caterer, int status) {
        return dishRepository.findByCatererEmailAndDishStatus(caterer, status);
    }
    public Dish findByDishID(Integer ID) {
        return dishRepository.findByDishID(ID);
    }
    public void save(Dish dish) {
        dishRepository.save(dish);
    }
}
