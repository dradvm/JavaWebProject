package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Report;
import com.JavaWebProject.JavaWebProject.repositories.ReportRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    
    public Report findById(int id) {
        Optional<Report> result = reportRepository.findById(id);
        return result.isPresent() ? result.get() : null;
    }
    
    public List<Report> findReportSentByCustomer() {
        return reportRepository.findReportSentByCustomer();
    }
    
    public List<Report> findReportSentByCaterer() {
        return reportRepository.findReportSentByCaterer();
    }
    
    public void save(Report report) {
        reportRepository.save(report);
    }
}
