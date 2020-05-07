package com.ysl.processing;

import android.graphics.Color;

import com.ysl.netty.udp.ChineseProverbClient;

import java.net.InetSocketAddress;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import processing.core.PApplet;

public class ProcessingRun extends PApplet{

    private ChineseProverbClient client;
    private int port = 8888;

    public void setup(){
        strokeWeight(20);
        stroke(Color.parseColor("red"));
        background(Color.parseColor("white"));
        client = new ChineseProverbClient();
        sendPosition();
    }
    public void draw(){
        line(mouseX,mouseY,pmouseX,pmouseY);
        System.out.println("mouseX = "+mouseX);
        System.out.println("mouseY = "+mouseY);
        System.out.println("pmouseX = "+pmouseX);
        System.out.println("pmouseY = "+pmouseY);
        Channel ch = client.getCh();
        if(ch != null && (mouseX != pmouseX || mouseY != pmouseY)){
            ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(
                    "mouseX = "+mouseX+"  mouseY = "+mouseY, CharsetUtil.UTF_8),
                    new InetSocketAddress("172.18.0.209", port)));
        }
    }

    private void sendPosition() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.run(port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

