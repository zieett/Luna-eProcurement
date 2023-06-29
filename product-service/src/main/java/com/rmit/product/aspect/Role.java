package com.rmit.product.aspect;


import com.rmit.product.enums.Permission;
import com.rmit.product.enums.Roles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Role {

    Roles role();

    Permission[] permissions() default {};
}
