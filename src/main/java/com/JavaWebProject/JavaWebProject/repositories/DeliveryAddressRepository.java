package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.models.DeliveryAddress;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryAddressRepository extends CrudRepository<DeliveryAddress, Integer> {
    
    List<DeliveryAddress> findByCustomerEmail(Customer customer);
}
