package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.City;
import com.JavaWebProject.JavaWebProject.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;
    
    public Iterable<City> findAll() {
        return cityRepository.findAll();
    }
}
