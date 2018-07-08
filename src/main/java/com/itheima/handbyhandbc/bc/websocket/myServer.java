package com.itheima.handbyhandbc.bc.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.handbyhandbc.bc.bean.Block;
import com.itheima.handbyhandbc.bc.bean.MessageBean;
import com.itheima.handbyhandbc.bc.bean.NoteBook;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;

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
            try {
                if ("亲，把你的区块链数据发我一份~".equals(message)){
                    NoteBook noteBook = NoteBook.getInstance();
                    ArrayList<Block> list = noteBook.showList();
                    // 发送给连接到本服务器的所有客户端
                    ObjectMapper objectMapper = new ObjectMapper();
                    String blockData = objectMapper.writeValueAsString(list);
                    MessageBean messageBean = new MessageBean(1,blockData);
                    String msg = objectMapper.writeValueAsString(messageBean);
                    // 广播消息
                    broadcast(msg);
                }
        } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
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
