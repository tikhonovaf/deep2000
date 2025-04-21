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
@Setter
@Table(name = "service")
public class BannerService {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue
    Long id;

    Long prevId;

    /**
     * Наименование
     */
    @NotNull
    String name;

    /**
     * Старое наименование
     */
    @NotNull
    String oldName;

    /**
     * Описание
     */
    String description;

    @ManyToOne
    BannerTypesByCatergory catFormat;

    @ManyToOne
    BannerTypesByCatergory catGoal;

    @ManyToOne
    BannerTypesByCatergory catPlace;

    @ManyToOne
    BannerTypesByCatergory catTechnology;

    Boolean confirmed;
    Boolean moderated;
    Integer price;

    @Lob
    private byte[] image;





}
