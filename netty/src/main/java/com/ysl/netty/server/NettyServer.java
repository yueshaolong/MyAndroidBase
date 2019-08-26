package com.ysl.netty.server;


import com.ysl.netty.protobuf.Message;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.protobuf.*;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class NettyServer {
    public static Logger logger = Logger.getLogger(NettyServer.class);
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(
//                            new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 1,0,1),
//                            new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Unpooled.copiedBuffer(ByteMessage.SEPARATOR.getBytes())),
                                    new IdleStateHandler(300, 0, 0, TimeUnit.SECONDS),
                                    new ProtobufEncoder(),
                                    new ProtobufDecoder(Message.SearchRequests.getDefaultInstance()),
                                    new ServerHandler());
                        }
                    });

            // 服务器绑定端口监听
            logger.info("netty服务端启动完成！！！" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
            b.bind(9966).sync().channel().closeFuture().sync();

        }catch (Exception e){
            logger.error("", e);
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
