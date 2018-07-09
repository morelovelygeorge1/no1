package com.example.demo.Model;

import lombok.Data;

@Data
public class User {
    private long uid;   //用户的ID
    private String username;    //用户名
}


/*
Create Table User(
    `uid` INT NOT NULL AUTO_INCREMENT,
    `username` TEXT NOT NULL,
    PRIMARY KEY(`uid`)
);
 */