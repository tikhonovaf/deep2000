package ru.atikhonov.deep2000.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.atikhonov.deep2000.backend.api.ServicesApiDelegate;
import ru.atikhonov.deep2000.backend.dto.ServiceInDto;
import ru.atikhonov.deep2000.backend.dto.ServiceViewDto;
import ru.atikhonov.deep2000.backend.exception.ValidateException;
import ru.atikhonov.deep2000.backend.mapper.ServiceMapper;
import ru.atikhonov.deep2000.backend.model.BannerService;
import ru.atikhonov.deep2000.backend.repository.ServiceRepository;
import ru.atikhonov.deep2000.backend.repository.ServiceViewRepository;
import ru.atikhonov.deep2000.backend.util.CoreUtil;

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
public class Services implements ServicesApiDelegate {


    private final ServiceRepository serviceRepository;
    private final ServiceViewRepository serviceViewRepository;
    private final ServiceMapper serviceMapper;
//    private final ApplicationContext applicationContext;


    /**
     * POST /demo2000/Services : Добавление сервиса
     *
     * @param serviceInDto 
     * @return Пустой ответ (status code 200)
     */
    @Override
    public ResponseEntity<Void> addService(ServiceInDto serviceInDto) {
        //  Проверяем уникальность наименования
        List<BannerService> bannerServices = serviceRepository.findAllByName(serviceInDto.getName());
        if (bannerServices.size() > 0) {
            throw ValidateException.exceptionSimple("Существует сервис с данным наименованием");
        }

        BannerService serviceNew = serviceMapper.fromDtoToEntity(serviceInDto);
        serviceRepository.save(serviceNew);

        return ResponseEntity.noContent().build();

    }

    /**
     * DELETE /demo2000/Services/{ServiceId} : Удаление сервиса
     *
     * @param ServiceId ИД элемента справочника (required)
     * @return Пустой ответ (status code 200)
     */
    @Override
    //  @UserAccess(action = ActionId.FULL, resource = ResourceId.CLUSTER)
    public ResponseEntity<Void> deleteService(Long ServiceId) {
        if (serviceRepository.findById(ServiceId).isPresent()) {
            BannerService bannerService = serviceRepository.findById(ServiceId).get();
            //  Удаляем все строки с разделами
            serviceRepository.delete(bannerService);

        }

        return ResponseEntity.noContent().build();

    }

    /**
     * GET /demo2000/Services/{ServiceId} : Выборка сервиса
     *
     * @param serviceId ИД элемента справочника (required)
     * @return Данные по элементу справочника (status code 200)
     */
    @Override
    public ResponseEntity<ServiceViewDto> getService(Long serviceId) {
        Optional<BannerService> bannerService = serviceRepository.findById(serviceId);
        if (bannerService.isPresent()) {
            ServiceViewDto result = serviceMapper
                    .fromViewToDto(serviceViewRepository.findById(serviceId).get());
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * GET /demo2000/Services : Выборка списка сервисов
     *
     * @return Список сервисов (status code 200)
     */
    @Override
    public ResponseEntity<List<ServiceViewDto>> getServices() {
        List<ServiceViewDto> result =
                serviceViewRepository
                        .findAll()
                        .stream()
                        .map(v -> serviceMapper.fromViewToDto(v))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /**
     * PATCH /demo2000/Services/{ServiceId} : Изменение сервиса
     *
     * @param serviceId    ИД сервиса (required)
     * @param serviceInDto (optional)
     * @return Пустое значение (status code 200)
     */
    @Override
//    @UserAccess(action = ActionId.FULL, resource = ResourceId.CLUSTER)
    public ResponseEntity<Void> modifyService(Long serviceId,
                                              ServiceInDto serviceInDto) {
        if (serviceRepository.findById(serviceId).isPresent()) {
            //  Проверяем уникальность наименования
            List<BannerService> bannerServices = serviceRepository.findAllByName(serviceInDto.getName());
            if (bannerServices.size() > 0 && !bannerServices.get(0).getId().equals(serviceId)) {
                throw ValidateException.exceptionSimple("Существует сервис с таким именем");
            }
            BannerService bannerService = bannerServices.get(0);
            BannerService bannerServiceNew = serviceMapper.fromDtoToEntity(serviceInDto);
            CoreUtil.patch(bannerServiceNew, bannerService);
            serviceRepository.save(bannerService);

        }
        return ResponseEntity.noContent().build();

    }

}


