package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Caterer;
import com.JavaWebProject.JavaWebProject.models.CatererRating;
import com.JavaWebProject.JavaWebProject.repositories.CatererRatingRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatererRatingService {

    @Autowired
    public CatererRatingRepository catererRatingRepository;

    public void save(CatererRating catererRating) {
        catererRatingRepository.save(catererRating);
    }
     public CatererRating findByOrderId(int orderID) {
        return catererRatingRepository.findByOrderID(orderID);
    }

     public List<CatererRating> findAllBannersByCaterer(Caterer catererEmail) {
        return catererRatingRepository.findByCatererEmail(catererEmail);
    
    }

}
