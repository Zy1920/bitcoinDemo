package com.itheima.ws;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashSet;

import javax.annotation.PostConstruct;

/**
 * author: 十年
 * signal: 爱生活爱陈奕迅
 * current time: 2018/7/4  21:27
 */
@RestController
public class wsController {
    private myServer server;
    private HashSet<String> set = new HashSet<>();

    @PostConstruct// 创建Controller后调用该方法
    public void init() {
        // webSocket服务器占用的端口号 = SpringBoot占用的端口号 +  1
        server = new myServer(Integer.parseInt(wsApplication.port) + 1);
        server.startServer();
    }

   //注册节点
   @RequestMapping("/regist")
    public String regist(String node){
        set.add(node);
        return "添加成功";
    }

    //连接
    @RequestMapping("/conn")
    public String conn(){
        try {
            for (String port : set) {
                URI uri=new URI("ws://localhost:" + port);
                myClient client = new myClient(uri, "连接到__" + port + "__服务器的客户端");
                client.connect();
            }
            return "连接成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "连接失败"+e.getMessage();
        }
    }


    //广播
    @RequestMapping("/broadcast")
    public String broadcast(String msg){
       server.broadcast(msg);
       return "服务端广播成功";
    }

}
