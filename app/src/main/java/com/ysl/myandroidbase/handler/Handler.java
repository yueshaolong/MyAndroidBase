package com.ysl.myandroidbase.handler;

public class Handler {

    private final MessageQueue messageQueue;

    public Handler() {
        Looper mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
                    "Can't create handler inside thread " + Thread.currentThread()
                            + " that has not called Looper.prepare()");
        }
        messageQueue = mLooper.messageQueue;
    }

    public boolean sendMessage(Message message){
        MessageQueue queue = messageQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(
                    this + " sendMessage() called with no mQueue");
            return false;
        }
        return enqueueMessage(queue, message);
    }

    private boolean enqueueMessage(MessageQueue queue, Message message) {
        message.target = this;
        return queue.enqueueMessage(message);
    }

    public void dispatchMessage(Message message) {
        handleMessage(message);
    }

    public void handleMessage(Message message) {
    }
}
