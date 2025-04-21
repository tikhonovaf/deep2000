package ru.atikhonov.deep2000.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.atikhonov.deep2000.backend.api.ServicesApi;
import ru.atikhonov.deep2000.backend.api.ServicesApiDelegate;
import ru.atikhonov.deep2000.backend.dto.FileDto;
import ru.atikhonov.deep2000.backend.dto.ServiceInDto;
import ru.atikhonov.deep2000.backend.dto.ServiceViewDto;
import ru.atikhonov.deep2000.backend.exception.ValidateException;
import ru.atikhonov.deep2000.backend.mapper.ServiceMapper;
import ru.atikhonov.deep2000.backend.model.*;
import ru.atikhonov.deep2000.backend.repository.*;
import ru.atikhonov.deep2000.backend.util.CoreUtil;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
public class Services implements ServicesApiDelegate {


    private final ServiceRepository serviceRepository;
    private final ServiceViewRepository serviceViewRepository;
    private final ServiceForCustomerViewRepository serviceForCustomerViewRepository;
    private final ServiceForModeratorViewRepository serviceForModeratorViewRepository;
    private final ServiceForSupportViewRepository serviceForSupportViewRepository;


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
        serviceNew.setConfirmed(false);  // Не утвержден
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
     * Тип запроса (C - customer, M - moderator, S - support)
     *
     * @return Список сервисов (status code 200)
     */
    @Override
    public ResponseEntity<List<ServiceViewDto>> getServices(String requestType) {
        List<ServiceViewDto> result = new ArrayList<>();
        if (requestType.equals("C")) {
            result =
                    serviceForCustomerViewRepository
                            .findAll()
                            .stream()
                            .map(v -> serviceMapper.fromViewToDto(v))
                            .collect(Collectors.toList());

        } else if (requestType.equals("M")) {
            result =
                    serviceForModeratorViewRepository
                            .findAll()
                            .stream()
                            .map(v -> serviceMapper.fromViewToDto(v))
                            .collect(Collectors.toList());

        } else if (requestType.equals("S")) {
            result =
                    serviceForSupportViewRepository
                            .findAll()
                            .stream()
                            .map(v -> serviceMapper.fromViewToDto(v))
                            .collect(Collectors.toList());
        }
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
    public ResponseEntity<Void> modifyService(Long serviceId,
                                              ServiceInDto serviceInDto) {
        if (serviceRepository.findById(serviceId).isPresent()) {
            //  Проверяем уникальность наименования
            List<BannerService> bannerServices = serviceRepository.findAllByName(serviceInDto.getName());
            if (bannerServices.size() > 0 && !bannerServices.get(0).getId().equals(serviceId)) {
                throw ValidateException.exceptionSimple("Существует сервис с таким именем");
            }
            BannerService bannerService = serviceRepository.findById(serviceId).get();
            BannerService bannerOld = bannerService;
            BannerService bannerServiceNew = serviceMapper.fromDtoToEntity(serviceInDto);
            if (bannerOld.getConfirmed()) {
                bannerServiceNew.setConfirmed(false);
                bannerServiceNew.setPrevId(bannerOld.getId());
                serviceRepository.save(bannerServiceNew);
            } else {
                CoreUtil.patch(bannerServiceNew, bannerService);
                serviceRepository.save(bannerService);
            }
        }
        return ResponseEntity.noContent().build();

    }

    /**
     * POST /services/image/upload/{serviceId} : Загрузка файла
     *
     * @param serviceId ИД сервиса (required)
     * @param file      (optional)
     * @return Пустой ответ (status code 200)
     * @see ServicesApi#uploadImage
     */
    @Override
    public ResponseEntity<Void> uploadImage(Long serviceId,
                                            MultipartFile file) {
        try {
            BannerService bannerService = serviceRepository.findById(serviceId).get();
            bannerService.setImage(file.getBytes());
            serviceRepository.save(bannerService);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * GET /services/image/download/{serviceId} : Выгрузка в файл
     *
     * @param serviceId ИД сервиса (required)
     * @return Файл (status code 200)
     * @see ServicesApi#downloadImageByServiceId
     */
    @Override
    public ResponseEntity<org.springframework.core.io.Resource> downloadImageByServiceId(Long serviceId) {
        BannerService bannerService = serviceRepository.findById(serviceId).get();
        String fileName = "image";

        HttpHeaders header = new HttpHeaders();
        try {
            header.add("Content-Disposition", "attachment; filename=" +
                    URLEncoder.encode(fileName, "UTF-8")
            );
        } catch (Exception e) {
            // do nothing - fileName always correct and exception will not catch
        }

        byte[] content = bannerService.getImage();
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(content.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileDto(fileName, content));

    }

    /**
     * PATCH /services/confirmation/{serviceId} : Подтверждение сервиса
     *
     * @param serviceId ИД сервиса (required)
     * @return Пустое значение (status code 200)
     * @see ServicesApi#confirmationService
     */
    @Override
    public ResponseEntity<Void> confirmationService(Long serviceId) {
        Optional<BannerService> bannerService = serviceRepository.findById(serviceId);
        if (bannerService.isPresent()) {
            bannerService.get().setConfirmed(true);
            serviceRepository.save(bannerService.get());
            //  если есть услуга, которая ссылается на эту, то ее удаляем
            Optional<BannerService> bannerServicePrev = serviceRepository.findById(bannerService.get().getPrevId());
            if (bannerServicePrev.isPresent()) {
                serviceRepository.delete(bannerServicePrev.get());
            }

        }
        return ResponseEntity.noContent().build();
    }
}
