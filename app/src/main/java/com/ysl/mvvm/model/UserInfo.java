package com.ysl.mvvm.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class UserInfo extends BaseObservable {
    private int age;
    private String name;

    public UserInfo() {
    }

    public UserInfo(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
