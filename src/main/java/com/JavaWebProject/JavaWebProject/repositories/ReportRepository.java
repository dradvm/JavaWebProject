package com.JavaWebProject.JavaWebProject.repositories;

import com.JavaWebProject.JavaWebProject.models.Report;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, Integer> {
    
    @Query(value = "select * from Report where Reporter in (select CustomerEmail from Customer)", nativeQuery = true)
    List<Report> findReportSentByCustomer();
}
