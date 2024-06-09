package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.Banner;
import com.JavaWebProject.JavaWebProject.models.BannerType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BannerRepository extends CrudRepository<Banner, Integer> {
    
    @Query("select count(u) from Banner u where u.bannerStartDate <= ?1 and u.bannerEndDate >= ?1")
    int countActiveBannerByDay(LocalDate date);
    
    @Query("select count(u) from Banner u where u.typeID = ?1 and u.bannerStartDate <= ?2 and u.bannerEndDate >= ?2")
    int countActiveBannerByTypeAndDay(BannerType type, LocalDate date);
    
    List<Banner> findAll();
}
