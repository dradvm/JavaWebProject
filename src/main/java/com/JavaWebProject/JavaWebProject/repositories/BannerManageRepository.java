/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.Banner;
import com.JavaWebProject.JavaWebProject.models.Caterer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author DELL
 */
public interface BannerManageRepository extends JpaRepository<Banner, Integer> {

    Optional<Banner> findByBannerID(int bannerID);

    List<Banner> findByCatererEmail(Caterer catererEmail);

    List<Banner> findAll();

}
