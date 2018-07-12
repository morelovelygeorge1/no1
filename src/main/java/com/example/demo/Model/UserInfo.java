package com.example.demo.Model;

import lombok.Data;

@Data
public class UserInfo {
    private int uid;
    private String company;
    private String title;
    private String position;
    private String phone;
    private String email;
    private String wechat;

    public UserInfo() {

    }

    public UserInfo(int uid, String company, String title, String position, String phone, String email, String wechat) {
        this.uid = uid;
        this.company = company;
        this.title = title;
        this.position = position;
        this.phone = phone;
        this.email = email;
        this.wechat = wechat;
    }
}

