package com.ysl.netty.client;

import com.ysl.netty.protobuf.Message;

import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyClient {
    public static Logger logger = Logger.getLogger(NettyClient.class);
    private static Bootstrap bootstrap;

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
                bootstrap = new Bootstrap();
                bootstrap.group(eventLoopGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel channel) throws Exception {
                                ChannelPipeline p = channel.pipeline();
                                p.addLast(
//                                        new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Unpooled.copiedBuffer(ByteMessage.SEPARATOR.getBytes())),
                                        new IdleStateHandler(0, 10, 0, TimeUnit.SECONDS),
                                        new ProtobufEncoder(),
                                        new ProtobufDecoder(Message.SearchResponse.getDefaultInstance()),
                                        new ClientHandler());
                            }
                        });

                ChannelFuture future = bootstrap.connect("192.168.1.24", 9966);
                future.addListener(channelFutureListener);
            }
        };
        new Thread(runnable).start();
    }

    private static ChannelFutureListener channelFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture f) throws Exception {
            if (f.isSuccess()) {
                logger.info("连接服务器成功");
            } else {
                logger.info("连接服务器失败");
                f.channel().eventLoop().schedule(new Runnable() {
                    @Override
                    public void run() {
                        connect();
                    }
                }, 5, TimeUnit.SECONDS);
            }
        }
    };

    public static void connect() {
        //试图去连接信令服务器
        try {
            Thread.sleep(5000);
            // 连接服务端
            ChannelFuture future = bootstrap.connect("192.168.1.208", 9966);
            future.addListener(channelFutureListener);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
