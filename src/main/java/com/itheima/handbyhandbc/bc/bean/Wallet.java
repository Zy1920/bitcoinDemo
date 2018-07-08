package com.itheima.handbyhandbc.bc.bean;

import com.itheima.handbyhandbc.bc.utils.RSAUtils;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * author: 十年
 * signal: 爱生活爱陈奕迅
 * current time: 2018/7/3  20:42
 */
public class Wallet {
    public PrivateKey privateKey;
    public PublicKey publicKey;


    public Wallet(String name){
        File publicFile = new File(name + ".pub");
        File privateFile = new File(name + ".pri");

        if (!publicFile.exists()||publicFile.length()==0||!privateFile.exists()||privateFile.length()==0){
            RSAUtils.generateKeysJS("RSA",name + ".pri",name + ".pub");
        }

        // 从文件中读取公私钥
        //PublicKey publicKey = RSAUtils.getPublicKeyFromFile("RSA", name + ".pub");
        //PrivateKey privateKey = RSAUtils.getPrivateKey("RSA", name + ".pri");
    }


    public Transaction sendMoney(String receiverPublickKey, String content){
        String publick = Base64.encode(publicKey.getEncoded());
        String signature = RSAUtils.getSignature("SHA256withRSA", privateKey, content);
        Transaction transaction = new Transaction(publick, receiverPublickKey, signature, content);
        return transaction;
    }

    public static void main(String[] args) {
        Wallet a = new Wallet("a");
        Wallet b = new Wallet("b");
    }
}
