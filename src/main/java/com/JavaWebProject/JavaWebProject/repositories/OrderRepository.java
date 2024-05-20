/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.CateringOrder;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author DELL
 */
public interface OrderRepository extends CrudRepository<CateringOrder, Integer> {
   
}
