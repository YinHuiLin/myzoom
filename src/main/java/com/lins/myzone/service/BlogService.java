package com.lins.myzoom.service;

import com.lins.myzoom.pojo.Blog;
import com.lins.myzoom.pojo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);
    Blog getAndConvert(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listBlog(Pageable pageable,String query);
    List<Blog> listBlogByTag(Long tagId);
    Page<Blog> listBlogByTag(Pageable pageable,Long tagId);
    Page<Blog> listBlogByType(Pageable pageable,Long typeId);
    List<Blog> listRecommendTop(Integer size);
    Map<String,List<Blog>> archivesBlog();
    int countAllPublishedBlog();
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id,Blog blog);
    void deleteBlog(Long id);
}
