package ru.atikhonov.deep2000.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.atikhonov.deep2000.backend.dto.BannerCategoryDto;
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
public class BannerCategoriesMapper {
    /**
     * Маппинг из entity в DTO
     *
     * @param entity - строка из entity
     * @return Данные в структуре DTO
     */
    public BannerCategoryDto fromEntityToDto(BannerCatergory entity) {
        BannerCategoryDto result = new BannerCategoryDto();
        CoreUtil.patch(entity, result);

        return result;
    }

}


