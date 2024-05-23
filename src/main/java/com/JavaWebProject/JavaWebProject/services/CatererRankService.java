package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.CatererRank;
import com.JavaWebProject.JavaWebProject.repositories.CatererRankRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatererRankService {
    @Autowired
    private CatererRankRepository catererRankRepository;
    
    public Iterable<CatererRank> findAll() {
        return catererRankRepository.findAll();
    }
    
    public CatererRank findById(int id) {
        Optional<CatererRank> result = catererRankRepository.findById(id);
        return result.isPresent() ? result.get() : null;
    }
}
