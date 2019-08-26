package com.ysl.netty.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ByteMessageType {
    ACCESS_TO_SIGNAL_HEART("接入服务和信令的心跳", 1),
    TERMINAL_CONNECTION("终端和接入服务器心跳连接状态", 2),
    INSTALL_TERMINAL_HEART("设置信令服务接入时终端的心跳", 3),
    TERMINAL_AND_SIGNAL_MESSAGE("终端和信令之间的通信消息", 4);

	private String value;
	private int code;
	private static Map<Integer, ByteMessageType> code2Authority = new HashMap<>();

	ByteMessageType(String value, int code) {
		this.setValue(value);
		this.setCode(code);
	}

	static {
		for (ByteMessageType authority : EnumSet.allOf(ByteMessageType.class)) {
			code2Authority.put(authority.getCode(), authority);
		}
	}

	public static ByteMessageType getInstanceByCode(int code) {
		return code2Authority.get(code);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
