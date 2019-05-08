package com.ysl.myaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


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

        //in 为定向 tag 的话表现为服务端将会接收到一个那个对象的完整数据，但是客户端的那个对象不会因为服务端对传参的修改而发生变动；
        @Override
        public Cat addCatIn(Cat cat) throws RemoteException {
            Log.d("", "In-->"+cat.toString());//In-->Cat{name='t', age=0, price=1}
            cat.name = "In";
            cat.price = 100;
            return cat;
        }

        //out 的话表现为服务端将会接收到那个对象的参数为空的对象，但是在服务端对接收到的空对象有任何修改之后客户端将会同步变动；
        @Override
        public Cat addCatOut(Cat cat) throws RemoteException {
            Log.d("", "Out-->"+cat.toString());//Out-->Cat{name='null', age=0, price=0}
            cat.name = "Out";
            cat.price = 101;
            return cat;
        }

        //inout 为定向 tag 的情况下，服务端将会接收到客户端传来对象的完整信息，并且客户端将会同步服务端对该对象的任何变动。
        @Override
        public Cat addCatInout(Cat cat) throws RemoteException {
            Log.d("", "Inout-->"+cat.toString());//Inout-->Cat{name='t', age=0, price=1}
            cat.name = "Inout";
            cat.price = 102;
            return cat;
        }
    };
}
