package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Admin;
import com.JavaWebProject.JavaWebProject.repositories.AdminRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    
    public Admin findById(String adminUsername) {
        Optional<Admin> result = adminRepository.findById(adminUsername);
        return result.isPresent() ? result.get() : null;
    }
    
    public void save(Admin admin) {
        adminRepository.save(admin);
    }
}
