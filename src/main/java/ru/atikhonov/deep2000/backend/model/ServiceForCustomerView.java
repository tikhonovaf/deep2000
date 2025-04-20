package ru.atikhonov.deep2000.backend.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
// import javax.validation.constraints.NotNull;

/**
 * Created by Tikhonov Arkadiy
 */
@Entity
@Getter
public class ServiceForCustomerView {

    /**
     * Идентификатор
     */
    @Id
    Long id;

    /**
     * Наименование
     */
    String name;

    /**
     * Старое наименование
     */
    String oldName;

    /**
     * Описание
     */
    String description;

    Integer catFormatId;
    String catFormatName;

    Integer catGoalId;
    String catGoalName;

    Integer catPlaceId;
    String catPlaceName;

    Integer catTechnologyId;
    String catTechnologyName;

    Boolean confirmed;
    Boolean moderated;
    Integer price;

}
