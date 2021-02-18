package com.lins.myzoom.service;

import com.lins.myzoom.pojo.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long id);
    Comment saveComment(Comment comment);
}
