package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Banner;
import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.repositories.BannerManageRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BannerManageService {

    @Autowired
    private BannerManageRepository bannerManageRepository;

    public Iterable<Banner> findAll() {
        return bannerManageRepository.findAll();
    }
    public void save(Banner banner) {
        bannerManageRepository.save(banner);
    }

    public Banner findById(int bannerID) {
        return bannerManageRepository.findByBannerID(bannerID).orElse(null);
    }

    public boolean isBannerActive(int bannerID) {
        Banner banner = findById(bannerID);
        if (banner != null) {
            Date now = new Date();
            return now.after(banner.getBannerStartDate()) && now.before(banner.getBannerEndDate());
        }
        return false;
    }

    public void delete(Banner banner) {
        bannerManageRepository.delete(banner);
    }

    public List<Banner> findAllBannersByCaterer(Caterer catererEmail) {
        return bannerManageRepository.findByCatererEmail(catererEmail);
    }
}
