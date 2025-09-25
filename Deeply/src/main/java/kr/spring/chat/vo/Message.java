package kr.spring.chat.vo;

import java.io.Serializable;

public class Message implements Serializable {

    private String user;
    private String msg;

    // 기본 생성자
    public Message() {}

    // 생성자
    public Message(String user, String msg) {
        this.user = user;
        this.msg = msg;
    }

    // getter, setter
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}