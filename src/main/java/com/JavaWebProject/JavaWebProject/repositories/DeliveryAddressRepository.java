package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.models.DeliveryAddress;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryAddressRepository extends CrudRepository<DeliveryAddress, Integer> {
    
    Iterable<DeliveryAddress> findByCustomerEmail(Customer customer);
}
