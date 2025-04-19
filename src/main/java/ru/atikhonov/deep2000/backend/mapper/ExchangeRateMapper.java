package ru.atikhonov.deep2000.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.atikhonov.deep2000.backend.dto.BannerCategoryDto;
import ru.atikhonov.deep2000.backend.dto.ExchangeRatesDto;
import ru.atikhonov.deep2000.backend.model.BannerCatergory;
import ru.atikhonov.deep2000.backend.model.ExchangeRate;
import ru.atikhonov.deep2000.backend.util.CoreUtil;

/**
 * Маппинг:
 * -  между entity и dto rest сервиса
 */
@Service
@RequiredArgsConstructor
public class ExchangeRateMapper {
    /**
     * Маппинг из entity в DTO
     *
     * @param entity - строка из entity
     * @return Данные в структуре DTO
     */
    public ExchangeRatesDto fromEntityToDto(ExchangeRate entity) {
        ExchangeRatesDto result = new ExchangeRatesDto();
        CoreUtil.patch(entity, result);
        result.setValue(entity.getValue());

        return result;
    }

}


