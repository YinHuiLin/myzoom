package com.lins.myzoom.service;

import com.lins.myzoom.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ClassName TagService
 * @Description TODO
 * @Author lin
 * @Date 2021/2/3 10:38
 * @Version 1.0
 **/
public interface TagService {
    Tag saveTag(Tag tag);
    Tag getTag(Long id);
    Page<Tag> listTag(Pageable pageable);
    List<Tag> listTag();
    List<Tag> listTag(String tags);
    List<Tag> listTop(Integer size);
    int publishedBlogsCount(Long id);
    Tag updateTag(Long id,Tag tag);
    void deleteTag(Long id);
    Tag getTagByName(String name);

}
