package com.ysl.rxjava.SyncThread;

public class SyncThread2 {

    private static int count;

    public synchronized void test1(){
        for (int i = 0; i < 5; i ++) {
            try {
                System.out.println(Thread.currentThread().getName() + ":" + (count++));
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void test2() {
        synchronized(this) {
            for (int i = 0; i < 5; i ++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static synchronized void test3(){
        for (int i = 0; i < 5; i ++) {
            try {
                System.out.println(Thread.currentThread().getName() + ":" + (count++));
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void test4() {
        synchronized(SyncThread2.class) {
            for (int i = 0; i < 5; i ++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SyncThread2 syncThread2 = new SyncThread2();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
//                syncThread2.test2();
//                new SyncThread2().test1();
//                syncThread2.test4();
//                new SyncThread2().test4();
//                syncThread2.test3();
                new SyncThread2().test3();
            }
        }, "A");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
//                syncThread2.test2();
//                new SyncThread2().test1();
//                syncThread2.test4();
//                new SyncThread2().test4();
//                syncThread2.test3();
                new SyncThread2().test3();
            }
        }, "B");

        thread1.start();
        thread2.start();
    }

}
