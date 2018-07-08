package com.itheima.ws;

import java.net.URI;

/**
 * author: 十年
 * signal: 爱生活爱陈奕迅
 * current time: 2018/7/4  20:57
 */
public class myTest {
    public static void main(String[] args) throws Exception {
        //开启一个服务器
        myServer myServer = new myServer(8000);
        myServer.startServer();

        //开启两个客户端
        URI uri = new URI("ws://localhost:8000");
        myClient client1 = new myClient(uri, "1111");
        myClient client2 = new myClient(uri, "2222");

        // 客户端连接服务器
        client1.connect();
        client2.connect();

        // 线程休眠，避免连接尚未成功,就发送消息,导致的发送失败
        Thread.sleep(2000);

        //服务器发送广播
       //myServer.broadcast("这是来自服务器的广播！~~~");


        //客户端发送消息
       client1.send("这是来自客户端1的消息！~~~");




    }
}
