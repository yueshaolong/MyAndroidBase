package com.ysl.netty.common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class MessageData implements Serializable{
    private static final long serialVersionUID = 1L;

    //接入服务器编号
    public int no;
    //成员自己的心跳
    public boolean isHeartBeat;
    //终端和接入的心跳
    public boolean connection;
    //设置终端的心跳状态
    public int heartbeatInterval;
    //心跳超时描述
    public int heartbeatTimeout;
    //成员的uuid
    public String uuid;
    public List<String> uuidList;
    //消息详情body
    public byte[] data;
    //消息的类型
    public ByteMessageType byteMessageType;

    @Override
    public String toString() {
        return "MessageData{" +
                "no=" + no +
                ", byteMessageType=" + byteMessageType +
                ", isHeartBeat=" + isHeartBeat +
                ", connection=" + connection +
                ", uuid='" + uuid + '\'' +
                ", data=" + Arrays.toString(data) +
                ", uuidList=" + uuidList +
                ", heartbeatInterval=" + heartbeatInterval +
                ", heartbeatTimeout=" + heartbeatTimeout +
                '}';
    }
}
