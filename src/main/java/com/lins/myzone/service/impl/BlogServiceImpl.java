package com.lins.myzoom.service.impl;

import com.lins.myzoom.dao.BlogRepository;
import com.lins.myzoom.dao.TypeRepository;
import com.lins.myzoom.exception.NotFoundException;
import com.lins.myzoom.pojo.Blog;
import com.lins.myzoom.pojo.Tag;
import com.lins.myzoom.pojo.Type;
import com.lins.myzoom.service.BlogService;
import com.lins.myzoom.pojo.BlogQuery;
import com.lins.myzoom.utils.MarkdownUtils;
import com.lins.myzoom.utils.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @ClassName BlogServiceImpl
 * @Description TODO
 * @Author lin
 * @Date 2021/2/3 11:38
 * @Version 1.0
 **/
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog=blogRepository.findById(id).orElse(null);
        if (blog==null){
            throw new NotFoundException("该博客不存在");
        }
        //新建一个blog，避免操作数据库
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String content=b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //views+1
        blogRepository.updateViews(id);
        return b;
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<>();
                if(!"".equals(blog.getTitle())&&blog.getTitle()!=null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId()!=null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if (blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAllByPublished(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, String query) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listBlogByTag(Long tagId) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<>();
                predicates.add(cb.equal(root.<Boolean>get("published"),true));
                Join join=root.join("tags");
                predicates.add(cb.equal(join.get("id"),tagId));
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        });
    }

    @Override
    public Page<Blog> listBlogByTag(Pageable pageable, Long tagId) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<>();
                predicates.add(cb.equal(root.<Boolean>get("published"),true));
                Join join=root.join("tags");
                predicates.add(cb.equal(join.get("id"),tagId));
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlogByType(Pageable pageable, Long typeId) {
        return blogRepository.findByTypeId(typeId,pageable);
    }

    @Override
    public List<Blog> listRecommendTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable=PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archivesBlog() {
        List<String> years=blogRepository.findGroupByYear();
        Map<String,List<Blog>> map=new HashMap<>();
        for(String year:years){
            List<Blog> blogsByYear=blogRepository.findByYear(year);
            map.put(year,blogsByYear);
        }
        return map;
    }

    @Override
    public int countAllPublishedBlog() {
        return blogRepository.countAllByPublished();
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        //新增
        if (blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }
        //编辑
        else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog blog1=blogRepository.findById(id).orElse(null);
        if(blog1==null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,blog1, MyBeanUtils.getNullPropertyNames(blog));
        blog1.setUpdateTime(new Date());
        return blogRepository.save(blog1);
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
