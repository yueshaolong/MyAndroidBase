package com.ysl.dagger2.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;
import javax.inject.Qualifier;

public class User {
    private String userName;
    private String pwd;
    private U u;

    //当类型不足以鉴别一个依赖的时候，我们就可以使用@Qulifier注解标示
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface QualifierA { }
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface QualifierB { }

//    @Inject
    public User(U u) {
        this.u = u;
    }

    public User() {
    }

    public User(String userName, String pwd) {
        this.userName = userName;
        this.pwd = pwd;
    }

    public void show(){
        System.out.println(userName+"_"+pwd);
        System.out.println("u:" + (u == null ? "null" : u.getMessage()));
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", pwd='" + pwd + '\'' +
                ", u=" + u +
                '}';
    }
}
