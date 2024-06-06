/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.models.OrderDetails;
import com.JavaWebProject.JavaWebProject.models.OrderDetailsPK;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author DELL
 */
public interface OrderDetailsRepository extends CrudRepository<OrderDetails, OrderDetailsPK> {
    int countByOrderDetailsPKDishID(int dishID);

    List<OrderDetails> findByOrderDetailsPKDishID(int dishID);
    
    Iterable<OrderDetails> findAllByOrderDetailsPKOrderID(Integer orderID);
}