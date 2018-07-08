package com.itheima.ws;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * author: 十年
 * signal: 爱生活爱陈奕迅
 * current time: 2018/7/4  20:44
 */
public class myClient extends WebSocketClient {
    private String name;
    public myClient(URI serverUri,String name) {
        super(serverUri);
        this.name=name;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("客户端"+name+"打开了连接！~~~");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("客户端"+name+"收到了消息！~~~"+message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("客户端"+name+"关闭了连接！~~~");
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("客户端"+name+"发生了错误！~~~");
    }
}
