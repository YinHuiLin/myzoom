package com.lins.myzoom.service.impl;

import com.lins.myzoom.dao.CommentRepository;
import com.lins.myzoom.pojo.Comment;
import com.lins.myzoom.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName CommentServiceImpl
 * @Description TODO
 * @Author lin
 * @Date 2021/2/7 15:36
 * @Version 1.0
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    private List<Comment> tempReplys=new ArrayList<>();
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort=Sort.by(Sort.Direction.ASC,"creatTime");
        List<Comment> comments=commentRepository.findByBlogIdAndParentCommentNull(blogId,sort);
        return eachComment(comments);
    }
    //copy每个评论节点，防止操作数据库
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentList=new ArrayList<>();
        for(Comment comment:comments){
            Comment c=new Comment();
            BeanUtils.copyProperties(comment,c);
            commentList.add(c);
        }
        //合并评论各层子代到第一级子代集合中
        combineChildren(commentList);
        return commentList;
    }
    //有父类评论的集合
    private void combineChildren(List<Comment> commentList) {
        for (Comment comment:commentList){
            List<Comment> replys=comment.getReplyComments();
            for (Comment reply:replys){
                //循环迭代，找出子代，存放在tempReply中
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清楚临时存放区
            tempReplys=new ArrayList<>();
        }
    }

    private void recursively(Comment comment) {
        tempReplys.add(comment);
        if(comment.getReplyComments().size()>0){
            List<Comment> replys=comment.getReplyComments();
            for(Comment reply:replys){
                tempReplys.add(reply);
                if(reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }

    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId=comment.getParentComment().getId();
        if(parentCommentId!=-1){
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        }
        else {
            comment.setParentComment(null);
        }
        comment.setCreatTime(new Date());
        return commentRepository.save(comment);
    }
}
