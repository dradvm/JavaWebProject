package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.CatererRank;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RankManageRepository extends JpaRepository<CatererRank, Integer> {

    List<CatererRank> findAll();

    @Query(value = "SELECT AVG(c.rankFee) FROM CatererRank c")
    double averageRankFee();

    @Query(value = "SELECT MAX(c.rankFee) FROM CatererRank c")
    double maxRankFee();

    @Query(value = "SELECT MIN(c.rankFee) FROM CatererRank c")
    double minRankFee();

    @Query(value = "SELECT AVG(c.rankCPO) FROM CatererRank c")
    double averageRankCPO();

    @Query(value = "SELECT MAX(c.rankCPO) FROM CatererRank c")
    double maxRankCPO();

    @Query(value = "SELECT MIN(c.rankCPO) FROM CatererRank c")
    double minRankCPO();

    @Query(value = "SELECT AVG(c.rankMaxDish) FROM CatererRank c")
    int averageRankMaxDish();

    @Query(value = "SELECT MAX(c.rankMaxDish) FROM CatererRank c")
    int maxRankMaxDish();

    @Query(value = "SELECT MIN(c.rankMaxDish) FROM CatererRank c")
    int minRankMaxDish();

    @Query("SELECT SUM(c.rankFee) FROM CatererRank c")
    Double getTotalRankFee();

    @Query("SELECT SUM(c.rankCPO) FROM CatererRank c")
    Double getTotalRankCPO();

    @Query("SELECT COUNT(c) FROM CatererRank c")
    int countAllRanks();

}
