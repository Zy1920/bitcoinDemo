package com.itheima.handbyhandbc.bc.websocket;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.handbyhandbc.bc.bean.Block;
import com.itheima.handbyhandbc.bc.bean.MessageBean;
import com.itheima.handbyhandbc.bc.bean.NoteBook;
import com.itheima.handbyhandbc.bc.bean.Transaction;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

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

        try {
            System.out.println("客户端"+name+"收到了消息！~~~"+message);
            ObjectMapper objectMapper = new ObjectMapper();
            NoteBook noteBook = NoteBook.getInstance();
            MessageBean messageBean = objectMapper.readValue(message, MessageBean.class);
            if (messageBean.getType()==1){
                // 收到的是区块链数据
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Block.class);
                ArrayList<Block> newList = objectMapper.readValue(messageBean.getMsg(), javaType);
                noteBook.compare(newList);
            }else if (messageBean.getType()==2){
                //收到的是交易数据
                Transaction transaction = objectMapper.readValue(messageBean.getMsg(), Transaction.class);
                if (transaction.verify()){
                    noteBook.addNote(messageBean.getMsg());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
