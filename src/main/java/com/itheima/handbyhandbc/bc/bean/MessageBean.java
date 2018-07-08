package com.itheima.handbyhandbc.bc.bean;

/**
 * author: 十年
 * signal: 爱生活爱陈奕迅
 * current time: 2018/7/8  13:20
 */
public class MessageBean {
    private int type;
    private String msg;

    public MessageBean() {
    }

    public MessageBean(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "type=" + type +
                ", msg='" + msg + '\'' +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
