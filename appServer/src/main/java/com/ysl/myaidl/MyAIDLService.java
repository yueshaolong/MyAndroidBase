package com.ysl.myaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;


import com.ysl.myaidl.bean.Cat;

public class MyAIDLService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    private IBinder myBinder = new ICatService.Stub(){

        @Override
        public Cat getCat() throws RemoteException {
            return new Cat("tom", 100);
        }

        @Override
        public int getCatCount() throws RemoteException {
            return 100;
        }

        @Override
        public void setCatPrice(Cat cat, int price) throws RemoteException {

        }

        @Override
        public void setCatName(Cat cat, String name) throws RemoteException {
            System.out.println("---------------->"+name);
        }

        @Override
        public void addCatIn(Cat cat) throws RemoteException {

        }

        @Override
        public void addCatOut(Cat cat) throws RemoteException {

        }

        @Override
        public void addCatInout(Cat cat) throws RemoteException {

        }
    };
}
