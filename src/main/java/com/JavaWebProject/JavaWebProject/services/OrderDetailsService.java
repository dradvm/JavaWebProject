/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.OrderDetails;
import com.JavaWebProject.JavaWebProject.repositories.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DELL
 */
@Service
public class OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public boolean isExistDishInOrderDetails(int dishID) {
        return orderDetailsRepository.countByOrderDetailsPKDishID(dishID) != 0 ? true : false;
    }

    public int countOrderSuccessWithDish(int dishID) {
        int count = 0;
        for (OrderDetails od : orderDetailsRepository.findByOrderDetailsPKDishID(dishID)) {
            if (od.getCateringOrder().getOrderState().equals("Finished")) {
                count++;
            }
        }
        return count;
    }

    public int countQuanitySuccessWithDish(int dishID) {
        int quantity = 0;
        for (OrderDetails od : orderDetailsRepository.findByOrderDetailsPKDishID(dishID)) {
            if (od.getCateringOrder().getOrderState().equals("Finished")) {
                quantity += od.getQuantity();
            }
        }
        return quantity;
    }

    public void save(OrderDetails od) {
        orderDetailsRepository.save(od);
    }

    public Iterable<OrderDetails> findAllByOrderID(Integer orderID) {
        return orderDetailsRepository.findAllByOrderDetailsPKOrderID(orderID);
    }

}
