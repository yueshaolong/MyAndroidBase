package com.ysl.netty.udp;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

public class ChineseProverbClient {

    private static EventLoopGroup group;

    public void run(int port) throws Exception {
        group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChineseProverbClientHandler());
            Channel ch = b.bind(0).sync().channel();
            //向网段内的所有机器广播
            while (true) {
                ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(
                        "谚语字典查询?", CharsetUtil.UTF_8), new InetSocketAddress(
                        "172.18.0.209", port))).sync();

                Thread.sleep(3000);
            }
            //客户端等待15s用于接收服务端的应答消息，然后退出并释放资源
//            if (!ch.closeFuture().await(15000)) {
//                System.out.println("查询超时！");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8888;
        if (args != null && args.length > 0) {
            port = Integer.valueOf(args[0]);
        }
        new ChineseProverbClient().run(port);
    }

    public static void stop(){
        if(group != null){
            group.shutdownGracefully();
        }
    }
}

