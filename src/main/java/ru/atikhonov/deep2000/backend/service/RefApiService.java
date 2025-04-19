package ru.atikhonov.deep2000.backend.service;//package ru.rndlab.migrator.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.atikhonov.deep2000.backend.api.ApiUtil;
import ru.atikhonov.deep2000.backend.api.RefApi;
import ru.atikhonov.deep2000.backend.api.RefApiDelegate;
import ru.atikhonov.deep2000.backend.dto.BannerCategoryDto;
import ru.atikhonov.deep2000.backend.dto.BannerTypeInDto;
import ru.atikhonov.deep2000.backend.dto.BannerTypesViewDto;
import ru.atikhonov.deep2000.backend.dto.ExchangeRatesDto;
import ru.atikhonov.deep2000.backend.mapper.BannerCategoriesMapper;
import ru.atikhonov.deep2000.backend.mapper.BannerTypesByCategoryMapper;
import ru.atikhonov.deep2000.backend.mapper.ExchangeRateMapper;
import ru.atikhonov.deep2000.backend.model.BannerTypesByCatergory;
import ru.atikhonov.deep2000.backend.model.BannerTypesByCatergoryView;
import ru.atikhonov.deep2000.backend.repository.BannerCategoryRepository;
import ru.atikhonov.deep2000.backend.repository.BannerTypesByCatergoryRepository;
import ru.atikhonov.deep2000.backend.repository.BannerTypesByCatergoryViewRepository;
import ru.atikhonov.deep2000.backend.repository.ExchangeRateRepository;
import ru.atikhonov.deep2000.backend.util.CoreUtil;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс для выполнения функций rest сервисов (GET, POST, PATCH, DELETE)
 *
 * @author Аркадий Тихонов
 */
@Service
@RequiredArgsConstructor
public class RefApiService implements RefApiDelegate {
    private final BannerTypesByCategoryMapper bannerTypesByCatergoryMapper;
    private final BannerTypesByCatergoryRepository bannerTypesByCatergoryRepository;
    private final BannerTypesByCatergoryViewRepository bannerTypesByCatergoryViewRepository;
    private final BannerCategoryRepository bannerCategoryRepository;
    private final BannerCategoriesMapper bannerCategoriesMapper;
    private final ExchangeRateMapper exchangeRateMapper;
    private final ExchangeRateRepository exchangeRateRepository;

    /**
     * POST /ref/bannerTypes : Добавление типа баннера
     *
     * @param bannerTypeInDto (optional)
     * @return Пустой ответ (status code 200)
     * @see RefApi#addBannerType
     */
    @Override
    public ResponseEntity<Void> addBannerType(BannerTypeInDto bannerTypeInDto) {
        BannerTypesByCatergory bannerTypesByCatergory = bannerTypesByCatergoryMapper.fromDtoToEntity(bannerTypeInDto);
        bannerTypesByCatergoryRepository.save(bannerTypesByCatergory);

        return ResponseEntity.noContent().build();

    }

    /**
     * DELETE /ref/bannerTypes/{bannerTypeId} : Удаление типа баннера
     *
     * @param bannerTypeId ИД элемента справочника (required)
     * @return Пустой ответ (status code 200)
     * @see RefApi#deleteBannerType
     */
    @Override
    public ResponseEntity<Void> deleteBannerType(Long bannerTypeId) {
        Optional<BannerTypesByCatergory> bannerTypesByCatergoryOptional = bannerTypesByCatergoryRepository.findById(bannerTypeId);
        if (bannerTypesByCatergoryOptional.isPresent()) {
            bannerTypesByCatergoryRepository.delete(bannerTypesByCatergoryOptional.get());
        }
        return ResponseEntity.noContent().build();

    }


    /**
     * GET /ref/bannerTypes : Выборка списка типов баннеров
     *
     * @return Список типов баннеров (status code 200)
     * @see RefApi#getBannerTypes
     */
    @Override
    public ResponseEntity<List<BannerTypesViewDto>> getBannerTypes() {
        List<BannerTypesViewDto> result =
                bannerTypesByCatergoryViewRepository
                        .findAll()
                        .stream()
                        .map(v -> bannerTypesByCatergoryMapper.fromViewToDto(v))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /**
     * GET /ref/bannerTypes/{bannerTypeId} : Выборка типа баннера
     *
     * @param bannerTypeId ИД элемента справочника (required)
     * @return Данные по элементу справочника (status code 200)
     * @see RefApi#getBannerType
     */
    @Override
    public ResponseEntity<BannerTypesViewDto> getBannerType(Long bannerTypeId) {
        Optional<BannerTypesByCatergoryView> bannerTypesByCatergoryOptional = bannerTypesByCatergoryViewRepository.findById(bannerTypeId);
        if (bannerTypesByCatergoryOptional.isPresent()) {
            BannerTypesViewDto result = bannerTypesByCatergoryMapper
                    .fromViewToDto(bannerTypesByCatergoryViewRepository.findById(bannerTypeId).get());
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * PATCH /ref/bannerTypes/{bannerTypeId} : Изменение типа баннера
     *
     * @param bannerTypeId    ИД (required)
     * @param bannerTypeInDto (optional)
     * @return Пустое значение (status code 200)
     * @see RefApi#modifyBannerType
     */
    @Override
    public ResponseEntity<Void> modifyBannerType(Long bannerTypeId,
                                                 BannerTypeInDto bannerTypeInDto) {
        if (bannerTypesByCatergoryRepository.findById(bannerTypeId).isPresent()) {

            BannerTypesByCatergory entity = bannerTypesByCatergoryRepository.findById(bannerTypeId).get();
            BannerTypesByCatergory entityNew = bannerTypesByCatergoryMapper.fromDtoToEntity(bannerTypeInDto);
            CoreUtil.patch(entityNew, entity);
            bannerTypesByCatergoryRepository.save(entity);
        }
        return ResponseEntity.noContent().build();

    }

    /**
     * GET /ref/bannerCategories : Выборка списка категорий
     *
     * @return Список категорий (status code 200)
     * @see RefApi#getBannerCategories
     */
    @Override
    public ResponseEntity<List<BannerCategoryDto>> getBannerCategories() {
        List<BannerCategoryDto> result =
                bannerCategoryRepository
                        .findAll()
                        .stream()
                        .map(v -> bannerCategoriesMapper.fromEntityToDto(v))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(result);

    }

    /**
     * GET /ref/exchangeRates : Выборка списка курсов валют
     *
     * @return Список курсов валют (status code 200)
     * @see RefApi#getExchangeRates
     */
    @Override
    public ResponseEntity<List<ExchangeRatesDto>> getExchangeRates() {
        List<ExchangeRatesDto> result =
                exchangeRateRepository
                        .findAll()
                        .stream()
                        .map(v -> exchangeRateMapper.fromEntityToDto(v))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(result);

    }

}
