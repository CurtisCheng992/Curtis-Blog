package com.curtis.curtisblog.mapper;

import com.curtis.curtisblog.entity.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 博客标签中间表的mapper
 */
@Mapper
public interface BlogTagsMapper {

    @Select("select id, name from t_tag where id in (select tags_id from t_blog_tags where blogs_id = #{blogId})")
    List<Tag> findByBlogId(Long blogId);

    @Insert("insert into t_blog_tags values(#{blogsId},#{tagsId})")
    void insertBlogTags(@Param("blogsId") Long blogsId, @Param("tagsId") Long tagsId);

    @Delete("delete from t_blog_tags where blogs_id = #{blogsId} and tags_id = #{tagsId}")
    void deleteBlogTags(@Param("blogsId") Long blogsId, @Param("tagsId") Long tagsId);

    @Delete("delete from t_blog_tags where blogs_id = #{blogsId}")
    void deleteAllByBlogId(Long blogsId);

}
