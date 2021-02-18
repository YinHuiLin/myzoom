package com.lins.myzoom.service.impl;

import com.lins.myzoom.dao.TypeRepository;
import com.lins.myzoom.exception.NotFoundException;
import com.lins.myzoom.pojo.Type;
import com.lins.myzoom.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName TypeServiceImpl
 * @Description TODO
 * @Author lin
 * @Date 2021/2/2 16:03
 * @Version 1.0
 **/
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private TypeService typeService;
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTop(Integer size) {
//        Sort sort=Sort.by(Sort.Direction.DESC,"blogs.size");
//        Pageable pageable=PageRequest.of(0,size,sort);
        List<Type> types=typeRepository.findAll();
        for (Type type:types){
            type.setPublishedBlogCount(typeService.publishedBlogsCount(type.getId()));
        }
        return types.stream().sorted(Comparator.comparing(Type::getPublishedBlogCount).reversed()).collect(Collectors.toList());
    }

    @Override
    public int publishedBlogsCount(Long id) {
        return typeRepository.publishedBlogCount(id);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type type1;
        type1 = typeRepository.findById(id).orElse(null);
        if(type1==null){
            throw new NotFoundException("不存在该分类");
        }
        BeanUtils.copyProperties(type,type1);
        return typeRepository.save(type1);
    }
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }
}
