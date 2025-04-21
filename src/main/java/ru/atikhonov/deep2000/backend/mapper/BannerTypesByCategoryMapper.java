package ru.atikhonov.deep2000.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.atikhonov.deep2000.backend.dto.BannerTypeInDto;
import ru.atikhonov.deep2000.backend.dto.BannerTypesViewDto;
import ru.atikhonov.deep2000.backend.model.BannerCatergory;
import ru.atikhonov.deep2000.backend.model.BannerTypesByCatergory;
import ru.atikhonov.deep2000.backend.model.BannerTypesByCatergoryView;
import ru.atikhonov.deep2000.backend.repository.BannerCategoryRepository;
import ru.atikhonov.deep2000.backend.util.CoreUtil;

import java.util.Optional;

/**
 * Маппинг:
 * -  между entity и dto rest сервиса
 */
@Service
@RequiredArgsConstructor
public class BannerTypesByCategoryMapper {
    private final BannerCategoryRepository bannerCategoryRepository;

    /**
     * Маппинг из view в DTO
     *
     * @param view - строка из entity
     * @return Данные в структуре DTO
     */
    public BannerTypesViewDto fromViewToDto(BannerTypesByCatergoryView view) {
        BannerTypesViewDto result = new BannerTypesViewDto();
        CoreUtil.patch(view, result);

        return result;
    }


    /**
     * Маппинг из DTO в Entity
     *
     * @param dto - строка из DTO
     * @return данные в структуре Entity
     */
    public BannerTypesByCatergory fromDtoToEntity(BannerTypeInDto dto) {
        BannerTypesByCatergory result = new BannerTypesByCatergory();
        CoreUtil.patch(dto, result);

        if (dto.getCatergoryId() != null) {
            Optional<BannerCatergory> bannerCatergory = bannerCategoryRepository.findById(dto.getCatergoryId());
            if (bannerCatergory.isPresent()) {
                result.setCatergory(bannerCatergory.get());
            }
        } else {
            result.setCatergory(null);
        }
        return result;
    }
}


