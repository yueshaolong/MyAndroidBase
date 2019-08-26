package com.ysl.rxjava.SyncThread;

import java.util.TimerTask;
import java.util.concurrent.*;

public class ThreadPool {

    private static TimerTask timerTask;
    private static Runnable runnable;
    private static Future<?> future;

    public static void main(String[] args) {
        ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(6);
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(6);
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("timerTask执行了。");
            }
        };

        runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable执行了。");
                if (timerTask != null) {
                    timerTask.cancel();
                    timerTask = null;
                    future.cancel(false);
                    System.out.println("timerTask取消了。");
                }
            }
        };
        /*future = scheduleThreadPool.schedule(timerTask, 5000, TimeUnit.MILLISECONDS);
        scheduleThreadPool.execute(timerTask);

        cachedThreadPool.execute(timerTask);
        future = cachedThreadPool.submit(timerTask);

        fixedThreadPool.execute(timerTask);
        future = fixedThreadPool.submit(timerTask);

        singleThreadExecutor.execute(timerTask);
        future = scheduleThreadPool.submit(timerTask);*/

        System.out.println("开启线程任务！1");
        future = scheduleThreadPool.scheduleAtFixedRate(timerTask, 5000, 2000, TimeUnit.MILLISECONDS);
        System.out.println("开启线程任务！2");
        scheduleThreadPool.schedule(runnable, 7000, TimeUnit.MILLISECONDS);


//        scheduleThreadPool.shutdown();
    }

}
