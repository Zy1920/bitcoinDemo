package com.itheima.ws;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * author: 十年
 * signal: 爱生活爱陈奕迅
 * current time: 2018/7/4  20:44
 */
public class myServer extends WebSocketServer {
    // 服务器端口
    private int port;

    public myServer(int port) {
        super(new InetSocketAddress(port));
        this.port = port;
    }


    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("websocket服务器"+port+"打开了连接！~~~");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("websocket服务器"+port+"关闭了连接！~~~");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("websocket服务器"+port+"收到了消息！~~~"+message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("websocket服务器"+port+"发生了错误！~~~");
    }

    @Override
    public void onStart() {
        System.out.println("websocket服务器"+port+"启动成功！~~~");
    }

    public void  startServer(){
        new Thread(this).start();
        System.out.println("websocket服务器"+port+"启动了！~~~");
    }
}
