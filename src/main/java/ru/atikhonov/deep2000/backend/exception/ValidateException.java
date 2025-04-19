package ru.atikhonov.deep2000.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Исключение
 */
@Getter
public class ValidateException extends RuntimeException {

    /**
     * Генерация исключения общего вида
     *
     * @param message текст сообщения
     * @return Сообщение об ошибке
     */
    public static ValidateException exceptionSimple(String message) {
        return new ValidateException(message, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Генерация исключения об отсутствии доступа
     *
     * @return Сообщение об ошибке
     */
    public static ValidateException accessDeniedException() {
        return new ValidateException("Access denied", HttpStatus.FORBIDDEN);
    }

    /**
     * Генерация исключения для сущностей, которые не найдены в БД по их идентификатору
     *
     * @param entityNames Список сущностей, не найденных в БД
     * @param entityIds Список идентификаторов, для которых не найдены сущности в БД
     * @return Сообщение об ошибке
     */
    public static ValidateException notFoundList(List<String> entityNames, List<Long>  entityIds) {
        String message = "";
        for (int i=0; i<entityNames.size(); i++) {
            if (i==0) {
                message = "Не найден объект - " + entityNames.get(i) + " с идентификатором = " + Long.toString(entityIds.get(i));
            } else {
                message = message + "; Не найден объект - " + entityNames.get(i) + " с идентификатором = " + Long.toString(entityIds.get(i));
            }
        }
        return new ValidateException(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Генерация ошибки, связанной с отстуствием записи в БД
     * @param entityName - наименование сущности
     * @param entityId - ИД сущности
     * @return - исключение
     */
    public static ValidateException notFound(String entityName, Long  entityId) {
        String message = "Не найден объект '" + entityName + "' с идентификатором = " + Long.toString(entityId);
        return new ValidateException(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Генерация ошибки, связанной с отстуствием записи в БД
     * @param entityName - наименование сущности
     * @param code - код сущности
     * @return - исключение
     */
    public static ValidateException notFoundByCode(String entityName, String code) {
        String message = "Не найден объект '" + entityName + "' с кодом = " + code;
        return new ValidateException(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Генерация исключения для сущностей, которые не являются уникальными по коду
     *
     * @param entityName Имя сущности
     * @param code Значение кода
     * @return Сообщение об ошибке
     */
    public static ValidateException notUniqieByCode(String entityName, String  code) {
        String message = "Для " + entityName + " значение кода = '" + code + "' не является уникальным";
        return new ValidateException(message, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Генерация исключения для сущностей, которые не являются уникальными по другим условиям
     *
     * @param entityName Имя сущности
     * @param reason Причина не уникальности
     * @return Сообщение об ошибке
     */
    public static ValidateException notUniqieByReason(String entityName, String  reason) {
        String message = entityName + " " + reason + "' не является уникальным";
        return new ValidateException(message, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Генерация внутренней ошибки, связанной с невозможностью удаления
     * @param entityName - текстовое наименование сущности
     * @param cause - порождающее исключение
     * @return исключение
     */
    public static ValidateException deletionNotPossible(String entityName, String  cause) {
        String message = "Удаление " + entityName + " невозможно. Причина: " + cause;
        return new ValidateException(message, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Генерация ошибки, связанной с конфликтом
     * @param message - сообщение
     * @return исключение
     */
    public static ValidateException conflict(String message) {
        return new ValidateException(message, HttpStatus.CONFLICT);
    }

    /**
     * Генерация ошибки, связанной с некорректным запросом
     * @param message - сообщение
     * @return исключение
     */
    public static ValidateException badRequest(String message) {
        return new ValidateException(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Генерация внутренней ошибки
     * @param message - сообщение
     * @return исключение
     */
    public static ValidateException internalError(String message) {
        return new ValidateException(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Генерация внутренней ошибки
     * @param message - сообщение
     * @param cause - порождающее исключение
     * @return исключение
     */
    public static ValidateException internalError(String message, Throwable cause) {
        return new ValidateException(message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }

    /**
     * Проверка с генерацией исключения
     * @param message - сообщение
     * @return исключение
     */
    public static ValidateException preconditionFailed(String message) {
        return new ValidateException(message, HttpStatus.PRECONDITION_FAILED);
    }

    private final HttpStatus status;

    /**
     * Конструктор
     * @param message - сообщение
     * @param status - HTTP-статус
     */
    public ValidateException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * Конструктор
     * @param message - сообщение
     * @param status - HTTP-статус
     * @param cause - порождающее исключение
     */
    public ValidateException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
