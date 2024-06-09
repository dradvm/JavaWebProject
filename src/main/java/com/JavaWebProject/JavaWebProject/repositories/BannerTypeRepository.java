package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.BannerType;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BannerTypeRepository extends CrudRepository<BannerType, Integer> {
    
    @Query("select u.typeDescription from BannerType u")
    List<String> getAllTypeDescription();
    
}
