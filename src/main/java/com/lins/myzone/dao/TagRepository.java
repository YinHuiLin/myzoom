package com.lins.myzoom.dao;

import com.lins.myzoom.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName TagRepository
 * @Description TODO
 * @Author lin
 * @Date 2021/2/3 10:42
 * @Version 1.0
 **/
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name);
    @Query("select t from t_tag t")
    List<Tag> findTop(Pageable pageable);
}
