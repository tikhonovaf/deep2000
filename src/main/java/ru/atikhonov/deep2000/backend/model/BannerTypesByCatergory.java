package ru.atikhonov.deep2000.backend.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Tikhonov Arkadiy
 */
@Entity
@Getter
@Setter
public class BannerTypesByCatergory {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue
    Long id;

    /**
     * Категория
     */
    @ManyToOne
    BannerCatergory catergory;

    /**
     * Наименование сервиса
     */
    @NotNull
    String name;


}
