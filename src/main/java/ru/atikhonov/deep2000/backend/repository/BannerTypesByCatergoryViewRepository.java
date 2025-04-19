package ru.atikhonov.deep2000.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atikhonov.deep2000.backend.model.BannerCatergory;
import ru.atikhonov.deep2000.backend.model.BannerTypesByCatergoryView;

import java.util.List;


public interface BannerTypesByCatergoryViewRepository extends JpaRepository<BannerTypesByCatergoryView, Long> {

//    public List<BannerTypesByCatergoryView> findAllByCategoryId(Long bannerCategoryId);

}
