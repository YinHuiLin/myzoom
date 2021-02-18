package com.lins.myzoom.service.impl;

import com.lins.myzoom.dao.BlogRepository;
import com.lins.myzoom.dao.TagRepository;
import com.lins.myzoom.exception.NotFoundException;
import com.lins.myzoom.pojo.Blog;
import com.lins.myzoom.pojo.Tag;
import com.lins.myzoom.pojo.Type;
import com.lins.myzoom.service.BlogService;
import com.lins.myzoom.service.TagService;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName TagServiceImpl
 * @Description TODO
 * @Author lin
 * @Date 2021/2/3 10:41
 * @Version 1.0
 **/
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private BlogService blogService;
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }
    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String tags) {//1,2,3
        return tagRepository.findAllById(convertToList(tags));
    }

    @Override
    public List<Tag> listTop(Integer size) {
//        Sort sort=Sort.by(Sort.Direction.DESC,"blogs.size");
//        Pageable pageable=PageRequest.of(0,size,sort);
//        return tagRepository.findTop(pageable);
//    }
        List<Tag> tags=tagRepository.findAll();
        for (Tag tag:tags){
            tag.setPublishedBlogCount(publishedBlogsCount(tag.getId()));
        }
        return tags.stream().sorted(Comparator.comparing(Tag::getPublishedBlogCount).reversed()).collect(Collectors.toList());
    }

    @Override
    public int publishedBlogsCount(Long TagId) {
        List<Blog> blogs=blogService.listBlogByTag(TagId);
        return blogs.size();
    }

    private List<Long> convertToList(String ids){
        List<Long> idList=new ArrayList<>();
        if(!"".equals(ids)&&ids!=null){
            String[] strings=ids.split(",");
            for(int i=0;i<strings.length;i++){
                idList.add(new Long(strings[i]));
            }
        }
        return idList;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag1=tagRepository.findById(id).orElse(null);
        if(tag1==null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,tag1);
        return tagRepository.save(tag1);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }
}
