package ru.atikhonov.deep2000.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.atikhonov.deep2000.backend.dto.ServiceInDto;
import ru.atikhonov.deep2000.backend.dto.ServiceViewDto;
import ru.atikhonov.deep2000.backend.model.*;
import ru.atikhonov.deep2000.backend.repository.BannerCategoryRepository;
import ru.atikhonov.deep2000.backend.repository.BannerTypesByCatergoryRepository;
import ru.atikhonov.deep2000.backend.repository.BannerTypesByCatergoryViewRepository;
import ru.atikhonov.deep2000.backend.repository.ServiceRepository;
import ru.atikhonov.deep2000.backend.util.CoreUtil;

import java.util.Optional;

/**
 * Маппинг:
 * -  между entity и dto rest сервиса
 */
@Service
@RequiredArgsConstructor
public class ServiceMapper {
    private final ServiceRepository projectRepository;
    private final BannerCategoryRepository bannerCategoryRepository;
    private final BannerTypesByCatergoryRepository bannerTypesByCatergoryRepository;

    /**
     * Маппинг из view в DTO
     *
     * @param view - строка из entity
     * @return Данные в структуре DTO
     */
    public ServiceViewDto fromViewToDto(Object view) {
        ServiceViewDto result = new ServiceViewDto();
        CoreUtil.patch(view, result);
        return result;
    }

    /**
     * Маппингиз DTO в Entity
     *
     * @param dto - строка из DTO
     * @return данные в структуре Entity
     */
    public BannerService fromDtoToEntity(ServiceInDto dto) {
        BannerService result = new BannerService();
        CoreUtil.patch(dto, result);

        if (dto.getCatFormatId() != null) {
            Optional<BannerTypesByCatergory> bannerTypesByCatergoryOptional = bannerTypesByCatergoryRepository.findById(dto.getCatFormatId());
            if (bannerTypesByCatergoryOptional.isPresent()) {
                result.setCatFormat(bannerTypesByCatergoryOptional.get());
            }
        } else {
            result.setCatFormat(null);
        }

        if (dto.getCatPlaceId() != null) {
            Optional<BannerTypesByCatergory> bannerTypesByCatergoryOptional = bannerTypesByCatergoryRepository.findById(dto.getCatPlaceId());
            if (bannerTypesByCatergoryOptional.isPresent()) {
                result.setCatPlace(bannerTypesByCatergoryOptional.get());
            }
        } else {
            result.setCatPlace(null);
        }

        if (dto.getCatGoalId() != null) {
            Optional<BannerTypesByCatergory> bannerTypesByCatergoryOptional = bannerTypesByCatergoryRepository.findById(dto.getCatGoalId());
            if (bannerTypesByCatergoryOptional.isPresent()) {
                result.setCatGoal(bannerTypesByCatergoryOptional.get());
            }
        } else {
            result.setCatGoal(null);
        }

        if (dto.getCatTechnologyId() != null) {
            Optional<BannerTypesByCatergory> bannerTypesByCatergoryOptional = bannerTypesByCatergoryRepository.findById(dto.getCatTechnologyId());
            if (bannerTypesByCatergoryOptional.isPresent()) {
                result.setCatTechnology(bannerTypesByCatergoryOptional.get());
            }
        } else {
            result.setCatTechnology(null);
        }
        return result;
    }
}


