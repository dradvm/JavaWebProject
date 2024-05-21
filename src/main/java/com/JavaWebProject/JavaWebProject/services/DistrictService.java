package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.District;
import com.JavaWebProject.JavaWebProject.repositories.DistrictRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictService {
    @Autowired
    private DistrictRepository districtRepository;
    
    public List<District> findAll() {
        return districtRepository.findAll();
    }
}
