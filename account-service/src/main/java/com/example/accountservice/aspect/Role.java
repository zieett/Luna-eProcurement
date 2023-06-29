package com.example.accountservice.aspect;

import com.example.accountservice.enums.Permission;
import com.example.accountservice.enums.Roles;

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
