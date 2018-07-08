package com.itheima.handbyhandbc.bc.bean;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.handbyhandbc.bc.utils.HashUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * author: 十年
 * signal: 爱生活爱陈奕迅
 * current time: 2018/7/3  9:34
 */
public class NoteBook {
    //在构造函数中加载本地数据

    private volatile static NoteBook instance;
    private NoteBook(){

    }
    public static NoteBook getInstance(){
        if(instance==null){
            synchronized (NoteBook.class){
                if(instance==null){
                    instance=new NoteBook();
                }
            }
        }
        return instance;
    }

    ArrayList<Block> list = new ArrayList<>();

    // 添加封面 = 创世区块
    // 添加封面的时候,必须保证账本是新的
    public void addGenesis(String genesis) {
        if (list.size() > 0) {
            throw new RuntimeException("添加封面的时候,必须保证账本是新的");
        }
        String preHash = "0000000000000000000000000000000000000000000000000000000000000000";
        int nonce = mine(preHash + genesis);
        list.add(new Block(
                list.size() + 1,
                genesis,
                HashUtils.sha256(nonce + preHash + genesis),
                nonce,
                preHash
        ));
        save2Disk();
    }

    // 添加交易记录 = 普通区块
    // 添加交易记录的时候,必须保证账本已经有封面了
    public void addNote(String note) {
        if (list.size() < 1) {
            throw new RuntimeException("添加交易记录的时候,必须保证账本已经有封面了");
        }
        String preHash = list.get(list.size() - 1).hash;
        int nonce = mine(preHash + note);
        list.add(new Block(
                list.size() + 1,
                note,
                HashUtils.sha256(nonce + preHash + note),
                nonce,
                preHash
        ));
        save2Disk();
    }

    // 展示数据
    public ArrayList<Block> showList() {

        return list;
    }

    // 保存到本地硬盘,增加保存数据到本地的方法
    public void save2Disk() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("a.json");
            objectMapper.writeValue(file, list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //增加加载本地数据的方法
    public void loadFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("a.json");
            //如果文件存在，读取之前的数据
            if (file.exists() && file.length() > 0) {
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Block.class);
                list = objectMapper.readValue(file, javaType);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 校验数据
    public String check() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            Block block = list.get(i);
            // 获取内容
            String content = block.content;
            //获取工作量证明
            int nonce = block.nonce;
            //获取prehash
            String preHash = block.preHash;
            //获取hash
            String hash = block.hash;
            int id = block.id;

            if (i == 0) {
                // 创世区块,校验hash
                preHash = "0000000000000000000000000000000000000000000000000000000000000000";
                String checkHash = HashUtils.sha256(nonce + preHash + content);
                // 比对hash,如果不一样说明数据内篡改
                if (!checkHash.equals(hash)) {
                    sb.append("编号为" + block.id + "的数据可能被篡改了，请注意防范黑客");
                }
            } else {
                String currentHash = HashUtils.sha256(nonce + preHash + content);
                if (!currentHash.equals(hash)) {
                    sb.append("编号为" + block.id + "的数据可能被篡改了，请注意防范黑客");
                }
                Block preBlock = list.get(i - 1);
                String preBlockHash = preBlock.hash;
                if (!preBlockHash.equals(preHash)) {
                    sb.append("编号为" + block.id + "的preHash有问题，请注意防范黑客");
                }
            }

        }
        return sb.toString();
    }


    //挖矿
    public int mine(String content) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String s = HashUtils.sha256(i + content);
            if (s.startsWith("0000")) {
                System.out.println("挖矿成功" + i);
                return i;
            } else {
                System.out.println("这是第" + i + "次尝试挖矿");
            }
        }
        throw new RuntimeException("挖矿失败");
    }


    public void compare(ArrayList<Block> newList) {
        //比较长度，校验
        if (newList.size()>list.size()){
            list=newList;
            save2Disk();
        }
    }
}


