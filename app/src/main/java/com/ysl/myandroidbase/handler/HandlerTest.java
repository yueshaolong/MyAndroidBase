package com.ysl.myandroidbase.handler;

import java.util.UUID;

public class HandlerTest {

    private static Handler handler2;

    public static void main(String[] args) {
        /////////////测试子线程发消息，主线程收消息///////////////////////
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
        //一个子线程向主线程发消息
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
        //多个子线程向主线程发送消息
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

        /////////////测试主线程发消息，子线程收消息///////////////////////
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();

                handler2 = new Handler(){
                    @Override
                    public void handleMessage(Message message) {
                        System.out.println("Thread："+ Thread.currentThread().getName()+"  子线程recv message："+message.toString());
                    }
                };

                Looper.loop();
            }
        }).start();

        while (true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = new Message(UUID.randomUUID().toString());
            System.out.println("Thread："+ Thread.currentThread().getName()+"  send message："+message.toString());
            handler2.sendMessage(message);
        }
    }
}
