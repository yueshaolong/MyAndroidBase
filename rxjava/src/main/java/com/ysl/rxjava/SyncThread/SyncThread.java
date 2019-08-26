package com.ysl.rxjava.SyncThread;

public class SyncThread implements Runnable {
    private static int count;
    private static Object object = new Object();

    @Override
    public  void run() {
        synchronized(/*this*/object) {
            for (int i = 0; i < 5; i++) {
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
		/*SyncThread s = new SyncThread();
        Thread t1 = new Thread(s);
        Thread t2 = new Thread(s);*/

        SyncThread s1 = new SyncThread();
        SyncThread s2 = new SyncThread();
        Thread t1 = new Thread(s1);
        Thread t2 = new Thread(s2);

        t1.start();
        t2.start();
    }
}