package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Customer;
import com.JavaWebProject.JavaWebProject.models.DeliveryAddress;
import com.JavaWebProject.JavaWebProject.repositories.DeliveryAddressRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryAddressService {
    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;
    
    public List<DeliveryAddress> findByCustomerEmail(Customer customer) {
        return deliveryAddressRepository.findByCustomerEmail(customer);
    }
    
    public DeliveryAddress findById(int id) {
        Optional<DeliveryAddress> result = deliveryAddressRepository.findById(id);
        return result.isPresent() ? result.get() : null;
    }
    
    public void save(DeliveryAddress address) {
        deliveryAddressRepository.save(address);
    }
    
    public void delete(DeliveryAddress address) {
        deliveryAddressRepository.delete(address);
    }
}
