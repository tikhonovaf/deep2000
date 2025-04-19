package ru.atikhonov.deep2000.backend.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
// import javax.validation.constraints.NotNull;

/**
 * Created by Tikhonov Arkadiy
 */
@Entity
@Getter
@Setter
public class ExchangeRate {

    @Id
    @GeneratedValue
    Long id;

    String numCode;

    String charCode;

    String name;

    BigDecimal value;

}
