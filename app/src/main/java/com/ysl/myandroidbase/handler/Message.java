package com.ysl.myandroidbase.handler;

public class Message {

    public String message;
    public Handler target;

    public Message(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }
}
