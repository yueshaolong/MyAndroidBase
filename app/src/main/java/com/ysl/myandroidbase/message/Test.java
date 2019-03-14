package com.ysl.myandroidbase.message;

public class Test {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue();

        int i = 0;
        while (i < 100){
            messageQueue.doEnqueue(i+"");
            i++;
        }

        for (;;){
            String s = messageQueue.doDequeue();
            if (s != null && Integer.valueOf(s) % 99 == 0){
                System.out.println("处理失败！");
                messageQueue.doEnqueue(s);
            }else {
                System.out.println("处理成功 "+s);
            }
        }
    }
}
