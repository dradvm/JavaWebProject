package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.District;
import com.JavaWebProject.JavaWebProject.repositories.DistrictRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictService {
    @Autowired
    private DistrictRepository districtRepository;
    
    public Iterable<District> findAll() {
        return districtRepository.findAll();
    }
    
    public District findById(int districtID) {
        Optional<District> result = districtRepository.findById(districtID);
        if (result.isPresent()) {
            return result.get();
        }
        else {
            return null;
        }
    }
}
