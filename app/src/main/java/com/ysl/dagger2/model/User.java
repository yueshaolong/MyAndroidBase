package com.ysl.dagger2.model;

import javax.inject.Inject;

public class User {
    private String userName;
    private String pwd;
    private U u;

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
//        System.out.println(userName+"_"+pwd);
        System.out.println("u:"+u.getMessage());
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
