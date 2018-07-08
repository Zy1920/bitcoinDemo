package com.itheima.handbyhandbc.bc.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.handbyhandbc.bc.BcApplication;
import com.itheima.handbyhandbc.bc.bean.Block;
import com.itheima.handbyhandbc.bc.bean.MessageBean;
import com.itheima.handbyhandbc.bc.bean.NoteBook;
import com.itheima.handbyhandbc.bc.bean.Transaction;
import com.itheima.handbyhandbc.bc.websocket.myClient;
import com.itheima.handbyhandbc.bc.websocket.myServer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;

import javax.annotation.PostConstruct;

/**
 * author: 十年
 * signal: 爱生活爱陈奕迅
 * current time: 2018/7/3  16:24
 */

@RestController
public class bcController {
    private NoteBook notebook = NoteBook.getInstance();
    private myServer server;
    private HashSet<String> set = new HashSet<>();

    @PostMapping("/addGenesis")
    public String addGenesis(String genesis) {
        try {
            notebook.addGenesis(genesis);
            return "success";
        } catch (Exception e) {
            return "fail:" + e.getMessage();
        }
    }

    @PostMapping("/addNote")
    public String addNote(Transaction transaction) {

        try {
            if (transaction.verify()) {
                ObjectMapper objectMapper = new ObjectMapper();
                String value = objectMapper.writeValueAsString(transaction);
                //封装消息bean
                MessageBean messageBean = new MessageBean(2, value);
                String msg = objectMapper.writeValueAsString(messageBean);
                //广播交易数据
                server.broadcast(msg);
                notebook.addNote(value);
                return "添加记录成功";
            }else {
                throw new RuntimeException("交易数据校验失败");
            }
        }
        catch (JsonProcessingException e) {
                return "fail:"+e.getMessage();
        }
    }




  /*  @PostMapping("/addNote")
    public String addNote(String note){
        try {
            notebook.addNote(note);
            return "success";
        } catch (Exception e) {
            return "fail:"+e.getMessage();
        }
    }*/

    @GetMapping("/showList")
    public ArrayList<Block> showList() throws Exception {
        Thread.sleep(1000);
        return notebook.showList();
    }

    @GetMapping("/check")
    public String check(){
        String check = notebook.check();
        if (check.length()==0){
            return "数据是安全的";
        }
        return check;
    }


    @PostConstruct// 创建Controller后调用该方法
    public void init() {
        // webSocket服务器占用的端口号 = SpringBoot占用的端口号 +  1
        server = new myServer(Integer.parseInt(BcApplication.port) + 1);
        server.startServer();
    }

    //注册节点
    @RequestMapping("/regist")
    public String regist(String node){
        System.out.println(node);
        set.add(node);
        System.out.println(set);
        return "添加成功";
    }

    private ArrayList<myClient> clients=new ArrayList<>();

    //连接
    @RequestMapping("/conn")
    public String conn(){
        try {
            for (String s : set) {
                URI uri=new URI("ws://localhost:" + s);
                System.out.println(s);
                myClient client = new myClient(uri, "连接到__" + s + "__服务器的客户端");
                client.connect();
                clients.add(client);
            }
            return "连接成功";
        } catch (Exception e) {
            return "连接失败"+e.getMessage();
        }
    }

    //广播
    @RequestMapping("/broadcast")
    public String broadcast(String msg){
        server.broadcast(msg);
        return "服务端广播成功";
    }

    //请求同步数据
    @RequestMapping("/syncData")
    public String syncData(){
        for (myClient client : clients) {
            client.send("亲，把你的区块链数据发我一份~");
        }
        return "广播成功";
    }
}
