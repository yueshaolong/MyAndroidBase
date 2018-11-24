package com.ysl.myandroidbase.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ysl.myaidl.ICatService;
import com.ysl.myaidl.bean.Cat;


public class MyAIDLService extends Service {
    public static final String TAG = "MyAIDLService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    private IBinder myBinder = new ICatService.Stub(){

        @Override
        public Cat getCat() throws RemoteException {
            return new Cat("tom", 200);
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

        }

        @Override
        public Cat addCatIn(Cat cat) throws RemoteException {
            Log.d(TAG, "In-->"+cat.toString());
            cat.name = "In";
            return cat;
        }

        @Override
        public Cat addCatOut(Cat cat) throws RemoteException {
            Log.d(TAG, "Out-->"+cat.toString());
            cat.name = "Out";
            cat.price = 11;
            return cat;
        }

        @Override
        public Cat addCatInout(Cat cat) throws RemoteException {
            Log.d(TAG, "Inout-->"+cat.toString());
            cat.name = "Inout";
            return cat;
        }
    };
}
