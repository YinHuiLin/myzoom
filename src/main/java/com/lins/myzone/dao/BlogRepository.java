package com.lins.myzoom.dao;

import com.lins.myzoom.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    @Query("select b from t_blog b where b.published=true")
    Page<Blog> findAllByPublished(Pageable pageable);
    @Query("select b from t_blog b where b.recommend=true and b.published=true")
    List<Blog> findTop(Pageable pageable);
    //select * from t_blog where title like '%内容%'
    @Query("select b from t_blog b where b.title like ?1 or b.content like ?1 and b.published=true")
    Page<Blog> findByQuery(String query,Pageable pageable);
    @Query("select b from t_blog b where b.type.id=?1 and b.published=true ")
    Page<Blog> findByTypeId(Long id,Pageable pageable);
    @Transactional
    @Modifying
    @Query("update t_blog b set b.views=b.views+1 where b.id=?1")
    int updateViews(Long id);
    @Query("select function('date_format',b.updateTime,'%Y') as year from t_blog b group by year order by function('date_format',b.updateTime,'%Y') desc ")
    List<String> findGroupByYear();
    @Query("select b from t_blog b where function('date_format',b.updateTime,'%Y')=?1 and b.published=true ")
    List<Blog> findByYear(String year);

    @Query("select count(b) from t_blog b where b.published=true")
    int countAllByPublished();
}
