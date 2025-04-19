package ru.atikhonov.deep2000.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atikhonov.deep2000.backend.model.BannerService;

import java.util.List;


public interface ServiceRepository extends JpaRepository<BannerService, Long> {
    List<BannerService> findAllByName(String name);
}
