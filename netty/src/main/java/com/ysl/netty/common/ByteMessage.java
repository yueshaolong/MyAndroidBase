package com.ysl.netty.common;

import org.apache.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ByteMessage {
    private static Logger logger = Logger.getLogger(ByteMessage.class);
    public static final int ACCESS_TO_SIGNAL_HEART_SIZE = 2;
    public static final int INSTALL_TERMINAL_HEART_SIZE = 20;
    public static final int CONNECT_TERMINAL_SIZE = 19;
    public static final String SEPARATOR = "";

    private static ByteMessage byteMessage;

    private ByteMessage(){
    }

    public synchronized static ByteMessage getInstance(){
        if (byteMessage == null) {
            byteMessage = new ByteMessage();
        }
        return byteMessage;
    }
    // 获取分隔符字节
    byte[] bytes = SEPARATOR.getBytes();
    public byte[] encodeHeartData(int no){
        if (no < 0){
            logger.warn("encodeHeartData出错！");
            return null;
        }
        // 终端心跳的类型，以数组为单位，一个数据就是一个类型
        byte[] data = new byte[ACCESS_TO_SIGNAL_HEART_SIZE + bytes.length ];
        ByteBuffer buffer = ByteBuffer.wrap(data)
                .put((byte)no)
                .put((byte) ByteMessageType.ACCESS_TO_SIGNAL_HEART.getCode());
        logger.info(Arrays.toString(buffer.array()));
        return buffer.array();
    }
    public byte[] encodeTerminalConnectionData(int no, String uuid, boolean connect){
        if (StringUtil.isEmpty(uuid) || no < 0){
            logger.warn("encodeTerminalConnectionData出错！");
            return null;
        }
        // 获取终端的连接状态
        byte[] data = new byte[CONNECT_TERMINAL_SIZE + bytes.length];
        ByteBuffer buffer = ByteBuffer.wrap(data).put((byte) no).put((byte) ByteMessageType.TERMINAL_CONNECTION.getCode());
        buffer.put(StringUtil.hexStringToByteArray(uuid));
        buffer.put((byte) getNumByConnect(connect));
        buffer.put(bytes);
        return buffer.array();
    }
    public byte[] encodeSetHeartData(int no, String uuid, int heartbeatInterval, int heartbeatTimeout){
        if (StringUtil.isEmpty(uuid) || no < 0){
            logger.warn("encodeSetHeartData出错！");
            return null;
        }
        // 获取终端的连接状态
        byte[] data = new byte[INSTALL_TERMINAL_HEART_SIZE+ bytes.length];
        ByteBuffer buffer = ByteBuffer.wrap(data).put((byte) no).put((byte) ByteMessageType.INSTALL_TERMINAL_HEART.getCode());
        buffer.put(StringUtil.hexStringToByteArray(uuid));
        buffer.put((byte) heartbeatInterval);
        buffer.put((byte) heartbeatTimeout);
        buffer.put(bytes);
        return buffer.array();
    }
    public byte[] encodeMessageData(int no, List<String> uuidList, byte[] messageByte){
        if (null == uuidList || uuidList.size() == 0 || null == messageByte || messageByte.length == 0 || no < 0){
            logger.warn("encodeMessageData出错！");
            return null;
        }
        // 获取终端的连接状态
        byte[] data = new byte[3 + uuidList.size() * 16 + messageByte.length + bytes.length];
        ByteBuffer buffer = ByteBuffer.wrap(data).put((byte) no).put((byte) ByteMessageType.TERMINAL_AND_SIGNAL_MESSAGE.getCode());
        buffer.put((byte) uuidList.size());
        for (String uuid : uuidList) {
            buffer.put(StringUtil.hexStringToByteArray(uuid));
        }
        buffer.put(messageByte);
        buffer.put(bytes);
        return buffer.array();
    }
    public int getNumByConnect(boolean connect){
        if (connect){
            return 1;
        }
        return 0;
    }
    public boolean getConnectByNum(int num){
        if (num == 1){
            return true;
        }
        if (num == 0){
            return false;
        }
        return false;
    }

    public MessageData decodeData(byte[] data){
        if (null == data || data.length <= 1){
            logger.warn("decodeData时，byte数组为空！");
            return null;
        }
        MessageData messageData = new MessageData();
        messageData.no = data[0];
        byte byteMessageType = data[1];
        if (byteMessageType == ByteMessageType.ACCESS_TO_SIGNAL_HEART.getCode()){
            messageData.byteMessageType = ByteMessageType.ACCESS_TO_SIGNAL_HEART;
            messageData.isHeartBeat = true;
        }else if (byteMessageType == ByteMessageType.TERMINAL_CONNECTION.getCode()){
            if (data.length < 19){
                logger.warn("decodeData时，连接状态数组异常！");
                return null;
            }
            messageData.byteMessageType = ByteMessageType.TERMINAL_CONNECTION;
            messageData.uuid = StringUtil.convert(Arrays.copyOfRange(data, 2, 18));
            messageData.connection = getConnectByNum(data[18]);
        }else if (byteMessageType == ByteMessageType.INSTALL_TERMINAL_HEART.getCode()){
            if (data.length < 20){
                logger.warn("decodeData时，心跳数组异常！");
                return null;
            }
            messageData.byteMessageType = ByteMessageType.INSTALL_TERMINAL_HEART;
            messageData.uuid = StringUtil.convert(Arrays.copyOfRange(data, 2, 18));
            messageData.heartbeatInterval = data[18];
            messageData.heartbeatTimeout = data[19];
        }else if (byteMessageType == ByteMessageType.TERMINAL_AND_SIGNAL_MESSAGE.getCode()){
            if (data.length < 20){
                logger.warn("decodeData时，消息数组异常！");
                return null;
            }
            messageData.byteMessageType = ByteMessageType.TERMINAL_AND_SIGNAL_MESSAGE;
            int uuidNum = data[2];
            if (uuidNum <= 0 || (3 + 16 * uuidNum) > data.length){
                logger.warn("decodeData时，消息数组中uuid数量和实际不相符！");
                return null;
            }
            List<String> uuidList = new ArrayList<>();
            for (int i = 0; i < uuidNum; i++) {
                byte[] bytes = Arrays.copyOfRange(data, 3 + 16 * i, 3 + 16 * (i + 1));
                String uuid = StringUtil.convert(bytes);
                uuidList.add(uuid);
            }
            messageData.uuidList = uuidList;
            messageData.data = Arrays.copyOfRange(data, 3 + 16 * uuidNum, data.length);
        }else {
            logger.warn("decodeData时，类型未找到！");
            return null;
        }
        return messageData;
    }

    public static void main(String[] args) {
        byte[] data = new byte[]{3, 1, 49, 1, 49};

        byte[] bytes = SEPARATOR.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(new byte[data.length+bytes.length]).put(data).put(bytes);
        System.out.println("--->"+Arrays.toString(bytes));
        System.out.println("--->"+Arrays.toString(buffer.array()));
    }

}
