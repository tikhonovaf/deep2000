package ru.atikhonov.deep2000.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atikhonov.deep2000.backend.model.BannerCatergory;
import ru.atikhonov.deep2000.backend.model.BannerTypesByCatergory;

import java.util.List;


public interface BannerTypesByCatergoryRepository extends JpaRepository<BannerTypesByCatergory, Long> {

    public List<BannerTypesByCatergory> findAllByCatergory(BannerCatergory BannerCatergory);

}
