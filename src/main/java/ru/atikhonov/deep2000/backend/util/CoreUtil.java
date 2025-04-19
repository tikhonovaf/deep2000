package ru.atikhonov.deep2000.backend.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс общих функций
 *
 * @author Аркадий Тихонов
 */

@Slf4j
public class CoreUtil<result> {

//      ---- =================  Общее =============== ------------------------
    /**
     * Маппинг атрибутов объекта одного класса в атрибуты объекта другого класса
     *
     * @param obj    Исходный объект
     * @param newObj Объект, значения атрибутов которого определяется
     */
    public static void patch(Object newObj, Object obj) {

        if (newObj == null) {
            obj = null;
            return;
        }

        Class cBase = obj.getClass();
        Class cNew = newObj.getClass();


        List<Field> fieldsBase = new ArrayList<>();
        getFieldsUpTo(cBase, fieldsBase);
        List<Field> fieldsNew = new ArrayList<>();
        getFieldsUpTo(cNew, fieldsNew);
        for (Field fieldBase : fieldsBase) {
            for (Field fieldNew : fieldsNew) {
                if (fieldBase.getName().equals(fieldNew.getName()) && !fieldBase.getName().equals("serialVersionUID")) {
                    try {
                        fieldBase.setAccessible(true);
                        fieldNew.setAccessible(true);
                    } catch (Exception e) {
                        log.info("===  patch  -  fieldBase.getName() = " + fieldBase.getName());
                    }
                    try {
                        Object value = fieldNew.get(newObj);
                        if (value != null) {
                            try {
                                fieldBase.set(obj, value);
                            } catch (IllegalArgumentException e) {
                                try {
                                    Object valueToSet = fieldBase.getType().newInstance();
                                    patch(value, valueToSet);
                                    fieldBase.set(obj, valueToSet);
                                } catch (InstantiationException ex) {
                                    // must be created always
                                }
                            }
//                        } else {
//                            fieldBase.set(obj, null);
                        }
                    } catch (IllegalAccessException e) {
                        // never catched because of setAccessible(true)
                    }
                }
            }
        }
    }

    /**
     * Уточнение списка полей класса
     *
     * @param startClass - класс
     * @param fields     - массив полей
     */
    public static void getFieldsUpTo(Class<?> startClass, List<Field> fields) {
        fields.addAll(Arrays.asList(startClass.getDeclaredFields()));

        Class<?> parentClass = startClass.getSuperclass();
        if (parentClass != null) {
            getFieldsUpTo(parentClass, fields);
        }
    }

}
