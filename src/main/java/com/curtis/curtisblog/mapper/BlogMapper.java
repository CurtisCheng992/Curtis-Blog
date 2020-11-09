package com.curtis.curtisblog.mapper;

import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.entity.BlogTags;
import com.curtis.curtisblog.entity.TopTypes;
import com.curtis.curtisblog.vo.BlogQuery;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

/**
 * 博客的mapper
 */
@Mapper
public interface BlogMapper {

    /**
     * 查询所有博客列表
     * @return
     */
    @Select("select * from t_blog order by update_time desc")
    @Results(id = "blogMap",
            value = {
                @Result(id = true, column = "id", property = "id"),
                @Result(column = "appreciation", property = "appreciation"),
                @Result(column = "comment_table", property = "commentTable"),
                @Result(column = "content", property = "content"),
                @Result(column = "create_time", property = "createTime"),
                @Result(column = "first_picture", property = "firstPicture"),
                @Result(column = "flag", property = "flag"),
                @Result(column = "published", property = "published"),
                @Result(column = "recommend", property = "recommend"),
                @Result(column = "share_statement", property = "shareStatement"),
                @Result(column = "title", property = "title"),
                @Result(column = "update_time", property = "updateTime"),
                @Result(column = "views", property = "views"),
                @Result(column = "description", property = "description"),
                @Result(column = "type_id", property = "type",
                        one = @One(select = "com.curtis.curtisblog.mapper.TypeMapper.findTypeById",fetchType = FetchType.EAGER)),
                @Result(column = "user_id", property = "user",
                        one = @One(select = "com.curtis.curtisblog.mapper.UserMapper.findUserById",fetchType = FetchType.EAGER)),
                @Result(column = "id", property = "comments",
                        many = @Many(select = "com.curtis.curtisblog.mapper.CommentMapper.findByBlogId",fetchType = FetchType.LAZY)),
                @Result(column = "id", property = "tags",
                        many = @Many(select = "com.curtis.curtisblog.mapper.BlogTagsMapper.findByBlogId",fetchType = FetchType.LAZY))
            })
    List<Blog> listAllBlog();


    /**
     * 根据查询条件查询博客
     * @return
     */
    @Select("<script>select * from t_blog where 1=1 <if test=\"title !=null and title != '' \"> and title = #{title} </if><if test=\"typeId !=null \"> and type_id = #{typeId} </if> <if test=\"recommend == true \"> and recommend = #{recommend} </if> order by update_time desc</script>")
    @ResultMap("blogMap")
    List<Blog> listBlogBySearch(BlogQuery blogQuery);

    /**
     * 根据id查询博客
     * @param id
     * @return
     */
    @Select("select * from t_blog where id = #{id}")
    @ResultMap("blogMap")
    Blog findBlogById(Long id);

    /**
     * 查询使用次数为前几的博客类型
     * @param size
     * @return
     */
    @Select("SELECT type_id,COUNT(1) AS frequency FROM t_blog GROUP BY type_id ORDER BY COUNT(1) DESC LIMIT 0,#{size}")
    @Results(id = "topTypes",
            value = {
            @Result(column = "frequency",property = "frequency"),
            @Result(column = "type_id",property = "type",
                one = @One(select = "com.curtis.curtisblog.mapper.TypeMapper.findTypeById",fetchType = FetchType.EAGER))
    })
    List<TopTypes> findTopTypes(Integer size);

    /**
     * 按照更新时间顺序查找推荐的前几的博客
     * @param size
     * @return
     */
    @Select("SELECT * FROM t_blog WHERE recommend=b'1' ORDER BY update_time DESC LIMIT 0, #{size}")
    @ResultMap("blogMap")
    List<Blog> findRecommendTopBlog(Integer size);

    /**
     * 根据条件全局搜索博客
     * @param query
     * @return
     */
    @Select("select * from t_blog where title like #{query} or content like #{query} order by update_time desc")
    @ResultMap("blogMap")
    List<Blog> findByQuery(String query);

    /**
     * 保存博客
     * @param blog
     */
    @Insert("insert into t_blog values(#{id},#{appreciation},#{commentTable},#{content},#{createTime},#{firstPicture},#{flag},#{published},#{recommend},#{shareStatement},#{title},#{updateTime},#{views},#{type.id},#{user.id},#{description})")
    @SelectKey(keyColumn = "id",keyProperty = "id",before = false,resultType = Long.class,statement = "select last_insert_id()")
    void saveBlog(Blog blog);


    /**
     * 更新博客
     * @param blog
     */
    @Update("update t_blog set appreciation=#{appreciation},comment_table=#{commentTable},content=#{content},create_time=#{createTime},first_picture=#{firstPicture},flag=#{flag},published=#{published},recommend=#{recommend},share_statement=#{shareStatement},title=#{title},update_time=#{updateTime},views=#{views},type_id=#{type.id},user_id=#{user.id},description=#{description} where id=#{id}")
    void updateBlog(Blog blog);


    /**
     * 删除博客
     * @param id
     */
    @Delete("delete from t_blog where id = #{id}")
    void deleteBlog(Long id);

    /**
     * 根据博客id更新浏览次数
     * @param id
     */
    @Update("update t_blog set views = views + 1 where id = #{id}")
    void updateViews(Long id);

    /**
     * 根据年份分组
     * @return
     */
    @Select("select date_format(b.update_time,'%Y') as year from t_blog b group by year order by year desc")
    List<String> findGroupYear();

    /**
     * 根据年份查询博客
     * @param year
     * @return
     */
    @ResultMap("blogMap")
    @Select("select * from t_blog b where date_format(b.update_time,'%Y') = #{year} order by update_time asc")
    List<Blog> findByYear(String year);

    /**
     * 查询所有博客条数
     * @return
     */
    @Select("select count(*) from t_blog")
    Long countBlog();

    @ResultMap("blogMap")
    @Select("select * from t_blog where id in (select blogs_id from t_blog_tags where tags_id = #{tagId}) order by update_time desc")
    List<Blog> listBlogByTagId(Long tagId);
}
