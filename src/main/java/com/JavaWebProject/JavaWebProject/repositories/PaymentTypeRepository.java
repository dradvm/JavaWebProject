/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.PaymentType;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author DELL
 */
<<<<<<< HEAD:src/main/java/com/JavaWebProject/JavaWebProject/repositories/PaymentTypeRepository.java
public interface PaymentTypeRepository extends CrudRepository<PaymentType, Long> {
    List<PaymentType> findAll();
=======
public interface OrderRepository extends CrudRepository<CateringOrder, Integer> {
   
>>>>>>> 1036a975d3fdde849f70e6b38410336413320847:src/main/java/com/JavaWebProject/JavaWebProject/repositories/OrderRepository.java
}
