package com.ysl.rxjava.SyncThread;

public class NotifyTest {
    private Object obj1 = new Object();
    private Object obj2 = new Object();

    public void fuc1(){
        synchronized (obj1){
            System.out.println("开始等待1："+Thread.currentThread().getName());
            try {
                obj1.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("取消等待1");
        }
    }
    public void fuc2(){
        synchronized (obj2){
            System.out.println("开始等待2："+Thread.currentThread().getName());
            try {
                obj2.wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("取消等待2");
        }
    }

    public Object getObj1() {
        return obj1;
    }

    public Object getObj2() {
        return obj2;
    }

    public static void main(String[] args) {
        NotifyTest notifyTest = new NotifyTest();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                notifyTest.fuc1();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                notifyTest.fuc2();
            }
        });

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (notifyTest.getObj1()) {
            notifyTest.getObj1().notifyAll();
        }
        synchronized (notifyTest.getObj2()) {
            notifyTest.getObj2().notifyAll();
        }
    }
}
