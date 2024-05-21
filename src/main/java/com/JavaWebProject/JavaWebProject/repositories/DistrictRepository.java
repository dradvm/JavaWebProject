package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.District;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface DistrictRepository extends CrudRepository<District, Integer> {
    
    List<District> findAll();
}
