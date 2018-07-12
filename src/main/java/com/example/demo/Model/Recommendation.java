package com.example.demo.Model;

import lombok.Data;

@Data
public class Recommendation {
    private int id;
    private int uid;
    private int rid;
    private int status;
    private String reason;

    public Recommendation() {

    }

    public Recommendation(int uid, int rid, String reason) {
        this.uid = uid;
        this.rid = rid;
        this.reason = reason;
    }
}
