package com.ysl.myandroidbase.handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageQueue {

    private final BlockingQueue<Message> arrayBlockingQueue = new ArrayBlockingQueue(10);

    public boolean enqueueMessage(Message message) {
        try {
            arrayBlockingQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Message next() {
        Message take = null;
        try {
            take = arrayBlockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return take;
    }
}
