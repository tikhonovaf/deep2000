package ru.atikhonov.deep2000.backend.access;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResourceId {
    ANIMAL(1l),
    ANIMAL_TYPE(2l);

    private Long id;
}
