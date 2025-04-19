package ru.atikhonov.deep2000.backend.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
// import javax.validation.constraints.NotNull;

/**
 * Created by Tikhonov Arkadiy
 */
@Entity
@Getter
public class ServiceView {

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
