package ru.atikhonov.deep2000.backend.access;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuditActionId {
    CREATE(1l),
    MODIFY(2l),
    DELETE(3l),
    COPY(4l),
    RUN(5l),
    STOP(6l),
    PAUSE(7l),
    RESUME(8l),
    SET_STATUS(9l),
    DELETE_FILE(10l);

    private Long id;
}
