package com.aspnt.mddl.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasActions {
    Action[] value();
    String codeDefPath() default "";
    ConditionActionsMode conditionActionsMode() default ConditionActionsMode.ONE_OF;

    enum Action {
        READ, CREATE, UPDATE, STATUS, DELETE
    }

    enum ConditionActionsMode {
        ONE_OF, ALL_OF
    }
}
