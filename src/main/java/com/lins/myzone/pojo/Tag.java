package com.lins.myzoom.pojo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Tag
 * @Description TODO
 * @Author lin
 * @Date 2021/2/1 15:47
 * @Version 1.0
 **/
@Entity(name = "t_tag")
@Table
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs=new ArrayList<>();
    @Transient
    private int publishedBlogCount;
    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public int getPublishedBlogCount() {
        return publishedBlogCount;
    }

    public void setPublishedBlogCount(int publishedBlogCount) {
        this.publishedBlogCount = publishedBlogCount;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                ", publishedBlogCount=" + publishedBlogCount +
                '}';
    }
}
