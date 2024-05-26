package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.CatererRank;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankManageRepository extends JpaRepository<CatererRank, Integer> {
    List<CatererRank> findAll();
}
