package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.BannerType;
import com.JavaWebProject.JavaWebProject.repositories.BannerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BannerTypeService {

    @Autowired
    private BannerTypeRepository bannerTypeRepository;

    // Phương thức để lấy tất cả BannerType
    public Iterable<BannerType> findAll() {
        return bannerTypeRepository.findAll();
    }

    // Phương thức để tìm BannerType theo ID
    public BannerType findById(Integer id) {
        Optional<BannerType> optional = bannerTypeRepository.findById(id);
        return optional.orElse(null);  // Hoặc xử lý lỗi nếu không tìm thấy
    }

    // Phương thức để lưu hoặc cập nhật BannerType
    public void save(BannerType bannerType) {
        bannerTypeRepository.save(bannerType);
    }

    // Phương thức để xóa BannerType
    public void deleteById(Integer id) {
        bannerTypeRepository.deleteById(id);
    }
}
