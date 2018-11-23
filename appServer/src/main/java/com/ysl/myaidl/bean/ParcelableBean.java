package com.ysl.myaidl.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ParcelableBean implements Parcelable {
    String aString;
    int aInt;
    double aDouble;
    HashMap<String, String> aHashMap;
    ArrayList<String> aArrayList;
    DemoAnotherClass anotherClass;

    static class DemoAnotherClass implements Parcelable{
        ConcurrentHashMap<String, String> aConcurrentHashMap;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeSerializable(this.aConcurrentHashMap);
        }

        public DemoAnotherClass() {
        }

        protected DemoAnotherClass(Parcel in) {
            this.aConcurrentHashMap = (ConcurrentHashMap<String, String>) in.readSerializable();
        }

        public static final Creator<DemoAnotherClass> CREATOR = new Creator<DemoAnotherClass>() {
            @Override
            public DemoAnotherClass createFromParcel(Parcel source) {
                return new DemoAnotherClass(source);
            }

            @Override
            public DemoAnotherClass[] newArray(int size) {
                return new DemoAnotherClass[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.aString);
        dest.writeInt(this.aInt);
        dest.writeDouble(this.aDouble);
        dest.writeSerializable(this.aHashMap);
        dest.writeStringList(this.aArrayList);
        dest.writeParcelable(this.anotherClass, flags);
    }

    public ParcelableBean() {
    }

    protected ParcelableBean(Parcel in) {
        this.aString = in.readString();
        this.aInt = in.readInt();
        this.aDouble = in.readDouble();
        this.aHashMap = (HashMap<String, String>) in.readSerializable();
        this.aArrayList = in.createStringArrayList();
        this.anotherClass = in.readParcelable(DemoAnotherClass.class.getClassLoader());
    }

    public static final Creator<ParcelableBean> CREATOR = new Creator<ParcelableBean>() {
        @Override
        public ParcelableBean createFromParcel(Parcel source) {
            return new ParcelableBean(source);
        }

        @Override
        public ParcelableBean[] newArray(int size) {
            return new ParcelableBean[size];
        }
    };
}
