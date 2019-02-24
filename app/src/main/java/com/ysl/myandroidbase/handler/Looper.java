package com.ysl.myandroidbase.handler;

public class Looper {
    static final ThreadLocal<Looper> mThreadLocal = new ThreadLocal<>();
    public final MessageQueue messageQueue;

    public static Looper myLooper() {
        return mThreadLocal.get();
    }
    public static void prepare(){
        mThreadLocal.set(new Looper());
    }
    public Looper(){
        messageQueue = new MessageQueue();
    }

    public static void loop(){
        Looper looper = Looper.myLooper();
        MessageQueue messageQueue = looper.messageQueue;
        for (;;){
            Message message = messageQueue.next();
            message.target.dispatchMessage(message);
        }
    }
}
