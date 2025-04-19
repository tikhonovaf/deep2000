package ru.atikhonov.deep2000.backend.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Tikhonov Arkadiy
 */
@Entity
@Getter
@Setter
public class BannerTypesByCatergoryView {

    /**
     * Идентификатор
     */
    @Id
    Long id;

    /**
     * Категория
     */
    Long catergoryId;

    /**
     * Наименование сервиса
     */
    String catergoryName;

    /**
     * Наименование сервиса
     */
    String name;


}
