package com.lins.myzoom.service;

import com.lins.myzoom.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName TypeService
 * @Description TODO
 * @Author lin
 * @Date 2021/2/2 16:01
 * @Version 1.0
 **/
public interface TypeService {
    Type saveType(Type type);
    Type getType(Long id);
    Page<Type> listType(Pageable pageable);
    List<Type> listType();
    List<Type> listTop(Integer size);
    int publishedBlogsCount(Long id);
    Type updateType(Long id,Type type);
    void deleteType(Long id);
    Type getTypeByName(String name);
}
