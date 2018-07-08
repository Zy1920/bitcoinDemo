package com.itheima.handbyhandbc.bc.bean;

/**
 * author: 十年
 * signal: 爱生活爱陈奕迅
 * current time: 2018/7/3  17:09
 */
public class Block {
    public int id;
    public String content;
    public String hash;
    public int nonce;
    public String preHash;

    public Block(){};

    public Block(int id, String content, String hash,int nonce,String preHash) {
        this.id = id;
        this.content = content;
        this.hash = hash;
        this.nonce=nonce;
        this.preHash=preHash;
    }
}
