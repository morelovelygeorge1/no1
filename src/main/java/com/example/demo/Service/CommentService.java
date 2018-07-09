package com.example.demo.Service;

import com.example.demo.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByPid(int pid);
    int comment(int pid, int uid, String content, String opinion);
}