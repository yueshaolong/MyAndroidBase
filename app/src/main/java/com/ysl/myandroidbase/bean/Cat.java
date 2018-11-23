package com.ysl.myandroidbase.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Cat implements Parcelable {
    public String name;
    public int age;

    public Cat() {
    }

    public Cat(Parcel source) {
        this.name = source.readString();
        this.age = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
    }
    /**
     * public static final一个都不能少，内部对象CREATOR的名称也不能改变，必须全部大写。
     * 重写接口中的两个方法：
     * createFromParcel(Parcel in) 实现从Parcel容器中读取传递数据值,封装成Parcelable对象返回逻辑层，
     * newArray(int size) 创建一个类型为T，长度为size的数组，供外部类反序列化本类数组使用。
     */
    public static final Creator<Cat> CREATOR = new Creator<Cat>() {
        /**
         * 从序列化后的对象中创建原始对象
         */
        @Override
        public Cat createFromParcel(Parcel source) {
            return new Cat(source);
        }
        /**
         * 创建指定长度的原始对象数组
         */
        @Override
        public Cat[] newArray(int size) {
            return new Cat[size];
        }
    };
}
