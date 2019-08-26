package com.ysl.rxjava.rxjava;

import io.reactivex.*;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.*;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        System.out.println("=========");
        deal();
        System.out.println("------------");
    }

    public static void deal(){
        Observable.intervalRange(20, 5, 1, 2, TimeUnit.SECONDS, Schedulers.trampoline())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("onNext : intervalRange : " + aLong);
                    }
                });
    }
}
