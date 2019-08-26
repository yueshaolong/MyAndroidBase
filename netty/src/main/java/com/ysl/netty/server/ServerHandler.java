package com.ysl.netty.server;

import com.ysl.netty.common.ByteMessage;
import com.ysl.netty.common.ByteMessageType;
import com.ysl.netty.common.MessageData;
import com.ysl.netty.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = Logger.getLogger(ServerHandler.class);
    private static Map<Integer, String> noToAddressMap = new HashMap<>();
    private static Map<String, ChannelHandlerContext> channelHandlerContextMap = new HashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("Netty Server active ");
        logger.info("服务，netty服务端通道激活 : " + ctx.channel().remoteAddress() +" active !");
        channelHandlerContextMap.put(ctx.channel().remoteAddress().toString(), ctx);
//        logger.info("通道激活后：channelHandlerContextMap = " + channelHandlerContextMap);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("Netty Server Inactive ");
        logger.info("信令服务，netty服务端通道断开 : " + ctx.channel().remoteAddress() + " Inactive !");
        channelHandlerContextMap.remove(ctx.channel().remoteAddress().toString());
//        logger.info("断开连接后：channelHandlerContextMap = "+channelHandlerContextMap);
        Iterator<Map.Entry<Integer, String>> iterator = noToAddressMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Integer, String> next = iterator.next();
            if (next.getValue().equals(ctx.channel().remoteAddress().toString())){
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        logger.info("netty Server 收到消息: ！！！"+ msg);
        responseHeartbeat(ctx);
//        MessageData messageData = ByteMessage.getInstance().decodeData((byte[]) msg);
//        logger.info("服务，netty服务端收到消息: ！！！"+messageData);
//        if (messageData == null){
//            logger.error("服务中，netty服务端收到消息为空 ！！！");
//            return;
//        }
//
//        if (messageData.byteMessageType == ByteMessageType.ACCESS_TO_SIGNAL_HEART){
//            logger.info("服务，收到"+messageData.no+"号netty客户端发来的心跳！！！");
//            noToAddressMap.put(messageData.no, ctx.channel().remoteAddress().toString());
//            logger.info("收到心跳后：noToAddressMap = "+noToAddressMap);
//        }else if (messageData.byteMessageType == ByteMessageType.TERMINAL_CONNECTION){
//            logger.info("服务，收到"+messageData.no+"号netty客户端发来的连接状态！！！");
//        }else if (messageData.byteMessageType == ByteMessageType.TERMINAL_AND_SIGNAL_MESSAGE){
//            logger.info("服务，收到"+messageData.no+"号netty客户端发来的命令！！！");
//        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        logger.info("netty Server channelReadComplete ！！！");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        logger.info("服务，netty服务端心跳检测；userEventTriggered !");

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                //未进行读操作
                logger.info("READER_IDLE");
                // 超时关闭channel
                ctx.close();
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {

            } else if (event.state().equals(IdleState.ALL_IDLE)) {

            }
        }
    }

    private void responseHeartbeat(ChannelHandlerContext ctx) {
        /*MessageData messageData = new MessageData();
        messageData.isHeartBeat = true;
        messageData.no = 1;
        messageData.byteMessageType = ByteMessageType.ACCESS_TO_SIGNAL_HEART;
        sendMessage(ctx, ByteMessageType.ACCESS_TO_SIGNAL_HEART, messageData);*/
        logger.info("服务端回复心跳！！！");
        Message.SearchResponse.Builder builder = Message.SearchResponse.newBuilder();
        builder.setResultCode(0);
        builder.setResult(Message.SearchResponse.ResultDes.newBuilder().setDes("成功").build());
        Message.SearchResponse response = builder.build();
        ctx.writeAndFlush(response);
    }
    private void sendMessage(ChannelHandlerContext ctx,  ByteMessageType byteMessageType, MessageData messageData)  {
        try {
            if (ctx == null || messageData == null){
                logger.error("netty服务端回复消息出错：ctx==null?"+(ctx == null)+", messageData == null?"+(messageData == null));
                return;
            }
//            logger.info("netty服务端《发送》》"+messageData);
            byte[] bytes = null;
            if (byteMessageType == ByteMessageType.ACCESS_TO_SIGNAL_HEART){
                bytes = ByteMessage.getInstance().encodeHeartData(messageData.no);
            }else if (byteMessageType == ByteMessageType.TERMINAL_CONNECTION){
                bytes = ByteMessage.getInstance().encodeTerminalConnectionData(messageData.no, messageData.uuid, messageData.connection);
            }else if (byteMessageType == ByteMessageType.TERMINAL_AND_SIGNAL_MESSAGE){
                bytes = ByteMessage.getInstance().encodeMessageData(messageData.no, messageData.uuidList, messageData.data);
            }
            if (null == bytes){
                logger.info("编码后的数组为空！");
                return;
            }
            logger.info("netty服务端发送消息；"+ Arrays.toString(bytes));
//            ctx.writeAndFlush(bytes);
            ctx.writeAndFlush("ack!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
