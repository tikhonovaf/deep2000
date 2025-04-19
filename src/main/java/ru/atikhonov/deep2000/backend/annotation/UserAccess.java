package ru.atikhonov.deep2000.backend.annotation;

import ru.atikhonov.deep2000.backend.access.ResourceId;
import ru.atikhonov.deep2000.backend.access.ActionId;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserAccess {
        ActionId action();
        ResourceId resource();
}
