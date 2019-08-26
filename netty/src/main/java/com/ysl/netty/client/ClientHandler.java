package com.ysl.netty.client;

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

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = Logger.getLogger(ClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("Netty Client active ");
        sendHeartbeat(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("Netty Client Inactive ");
        NettyClient.connect();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        logger.info("netty Client 收到消息: ！！！"+ msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        logger.info("netty Client channelReadComplete ！！！");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        logger.info("netty客户端心跳检测；userEventTriggered");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {

            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                logger.info("netty客户端，WRITER_IDLE");
                sendHeartbeat(ctx);
            } else if (event.state().equals(IdleState.ALL_IDLE)) {

            }
        }
    }

    private void sendHeartbeat(ChannelHandlerContext ctx) {
        /*MessageData messageData = new MessageData();
        messageData.isHeartBeat = true;
        messageData.no = 1;
        messageData.byteMessageType = ByteMessageType.ACCESS_TO_SIGNAL_HEART;
        sendMessage(ctx, ByteMessageType.ACCESS_TO_SIGNAL_HEART, messageData);*/
        logger.info(1+"号netty客户端，向服务端发送心跳！！！");
        Message.SearchRequests.Builder builder = Message.SearchRequests.newBuilder();
        builder.setQuery("查询");
        builder.setPageNumber(1);
        builder.setResultPerPage(1);
        Message.SearchRequests requests = builder.build();
        ctx.writeAndFlush(requests);
    }
    private void sendMessage(ChannelHandlerContext ctx,  ByteMessageType byteMessageType, MessageData messageData)  {
        try {
            if (ctx == null || messageData == null){
                logger.error("netty客户端发送消息出错：ctx==null?"+(ctx == null)+", messageData == null?"+(messageData == null));
                return;
            }
//            logger.info("netty客户端《发送》》"+messageData);
            byte[] bytes = null;
            if (byteMessageType == ByteMessageType.ACCESS_TO_SIGNAL_HEART){
                bytes = ByteMessage.getInstance().encodeHeartData(messageData.no);
            }else if (byteMessageType == ByteMessageType.TERMINAL_CONNECTION){
                bytes = ByteMessage.getInstance().encodeTerminalConnectionData(messageData.no, messageData.uuid, messageData.connection);
            }else if (byteMessageType == ByteMessageType.TERMINAL_AND_SIGNAL_MESSAGE){
                bytes = ByteMessage.getInstance().encodeMessageData(messageData.no, messageData.uuidList, messageData.data);
            }
            if (null == bytes){
                logger.info("netty客户端，编码后的数组为空！");
                return;
            }
            logger.info("netty客户端发送消息；"+Arrays.toString(bytes));
            ctx.writeAndFlush(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
