package com.ysl.myandroidbase.handler;

import java.util.UUID;

public class HandlerTest {
    public static void main(String[] args) {
        Looper.prepare();

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message message) {
                System.out.println("Thread："+ Thread.currentThread().getName()+"  recv message："+message.toString());
            }
        };
        final Handler handler1 = new Handler(){
            @Override
            public void handleMessage(Message message) {
                System.out.println("Thread："+ Thread.currentThread().getName()+"  handler1---->recv message："+message.toString());
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    Message message = new Message(UUID.randomUUID().toString());
                    handler1.sendMessage(message);
                    System.out.println("Thread："+ Thread.currentThread().getName()+"  handler1---->send message："+message.toString());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        for (int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        String uuid;
                        synchronized (UUID.class){
                            uuid = UUID.randomUUID().toString();
                        }
                        Message message = new Message(uuid);
                        handler.sendMessage(message);
                        System.out.println("Thread："+ Thread.currentThread().getName()+"  send message："+message.toString());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        Looper.loop();
    }
}
