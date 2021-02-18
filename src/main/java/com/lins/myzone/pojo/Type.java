package com.lins.myzoom.pojo;

import com.lins.myzoom.dao.TypeRepository;
import com.lins.myzoom.service.BlogService;
import com.lins.myzoom.service.TypeService;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Type
 * @Description TODO
 * @Author lin
 * @Date 2021/2/1 15:45
 * @Version 1.0
 **/
@Entity(name = "t_type")
@Table
public class Type {
    @Transient
    @Autowired
    private TypeRepository typeRepository;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="分类名称不能为空")
    private String name;
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs=new ArrayList<>();
    @Transient
    private int publishedBlogCount;
    public Type() {
    }
    public void init(){
        this.publishedBlogCount=typeRepository.publishedBlogCount(this.id);
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
        return "Type{" +
                "typeRepository=" + typeRepository +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                ", publishedBlogCount=" + publishedBlogCount +
                '}';
    }
}
