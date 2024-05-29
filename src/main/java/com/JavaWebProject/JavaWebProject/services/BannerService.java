package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.BannerType;
import com.JavaWebProject.JavaWebProject.repositories.BannerRepository;
import com.JavaWebProject.JavaWebProject.repositories.BannerTypeRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerService {
    @Autowired
    private BannerRepository bannerRepository;
    @Autowired
    private BannerTypeRepository bannerTypeRepository;
    
    public int countActiveBannerByDay(LocalDate date) {
        return bannerRepository.countActiveBannerByDay(date);
    }
    
    public int getActiveBannerGapByDay(LocalDate date) {
        return countActiveBannerByDay(date) - countActiveBannerByDay(date.minusDays(1));
    }
    
    public List<String> getAllTypeDescription() {
        return bannerTypeRepository.getAllTypeDescription();
    }
    
    public List<Integer> countActiveBannerPerTypeByDay(LocalDate date) {
        List<Integer> result = new ArrayList();
        Iterable<BannerType> types = bannerTypeRepository.findAll();
        types.forEach((type) -> {
            result.add(bannerRepository.countActiveBannerByTypeAndDay(type, date));
        });
        return result;
    }
}
