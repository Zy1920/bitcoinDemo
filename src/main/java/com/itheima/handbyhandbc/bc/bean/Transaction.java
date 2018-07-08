package com.itheima.handbyhandbc.bc.bean;

import com.itheima.handbyhandbc.bc.utils.RSAUtils;

import java.security.PublicKey;

/**
 * author: 十年
 * signal: 爱生活爱陈奕迅
 * current time: 2018/7/3  20:52
 */
public class Transaction {
    // 付款方的公钥
    public String senderPublicKey;
    // 收款方的公钥
    public String receiverPublicKey;
    // 签名
    public String signature;
    // 转账信息
    public String content;

    public Transaction() {
    }

    public Transaction(String senderPublicKey, String receiverPublicKey, String signature, String content) {
        this.senderPublicKey = senderPublicKey;
        this.receiverPublicKey = receiverPublicKey;
        this.signature = signature;
        this.content = content;
    }

    public boolean verify(){
        PublicKey sender = RSAUtils.getPublicKeyFromString("RSA", senderPublicKey);
        return  RSAUtils.verifyDataJS("SHA256withRSA",sender,content,signature);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "senderPublicKey='" + senderPublicKey + '\'' +
                ", receiverPublicKey='" + receiverPublicKey + '\'' +
                ", signature='" + signature + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    public String getReceiverPublicKey() {
        return receiverPublicKey;
    }

    public void setReceiverPublicKey(String receiverPublicKey) {
        this.receiverPublicKey = receiverPublicKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
