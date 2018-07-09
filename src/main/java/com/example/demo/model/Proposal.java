package com.example.demo.model;

import lombok.Data;

@Data
public class Proposal {
    private int pid;
    private String name;
    private String content;
    private int author;
    private long timeline;
    private long deadline;
    private int status;
    private int support;
    private int reject;

    public Proposal() {

    }

    public Proposal(int author, String name, String content) {
        this.author = author;
        this.name = name;
        this.content = content;
        this.timeline = System.currentTimeMillis() / 1000;//获取系统当前时间
        this.deadline = this.timeline + 604800;//获取系统当前时间之后七天
    }
}