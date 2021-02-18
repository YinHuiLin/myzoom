package com.lins.myzoom.dao;

import com.lins.myzoom.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName TypeRepository
 * @Description TODO
 * @Author lin
 * @Date 2021/2/2 16:04
 * @Version 1.0
 **/
public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);
    @Query("select t from t_type t")
//    @Query("select t_type from t_type,t_blog WHERE t_type.id=t_blog.type.id AND t_blog.published=TRUE")
    List<Type> findTop(Pageable pageable);
    @Query("select count(b) from t_blog b where b.published=true and b.type.id=?1")
    int publishedBlogCount(Long typeId);
}
