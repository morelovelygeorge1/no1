package com.example.demo.model;

import lombok.Data;

@Data
public class Comment {
    private int cid;
    private int uid;
    private int pid;
    private String content;
    private long timeline;

    public Comment() {

    }

    public Comment( int pid, int uid, String content) {
        this.uid = uid;
        this.pid = pid;
        this.content = content;
        this.timeline = System.currentTimeMillis() / 1000;
    }
}
