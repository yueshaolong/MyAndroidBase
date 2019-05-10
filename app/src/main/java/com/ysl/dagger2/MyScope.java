package com.ysl.dagger2;

import javax.inject.Scope;

@Scope
public @interface MyScope {
    int id() default -1;
    String msg() default "Hello";
}
