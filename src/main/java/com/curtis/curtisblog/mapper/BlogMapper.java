package com.curtis.curtisblog.mapper;

import com.curtis.curtisblog.entity.Blog;
import com.curtis.curtisblog.vo.BlogQuery;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

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
    @Select("select * from t_blog")
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
                @Result(column = "type_id", property = "type",
                        one = @One(select = "com.curtis.curtisblog.mapper.TypeMapper.findTypeById",fetchType = FetchType.EAGER)),
                @Result(column = "user_id", property = "user",
                        one = @One(select = "com.curtis.curtisblog.mapper.UserMapper.findUserById",fetchType = FetchType.EAGER)),
                @Result(column = "id", property = "tags",
                        many = @Many(select = "com.curtis.curtisblog.mapper.BlogTagsMapper.findByBlogId",fetchType = FetchType.LAZY))
            })
    List<Blog> listAllBlog();


    /**
     * 根据查询条件查询博客
     * @return
     */
    @Select("<script>select * from t_blog where 1=1 <if test=\"title !=null and title != '' \"> and title = #{title} </if><if test=\"typeId !=null \"> and type_id = #{typeId} </if> <if test=\"recommend == true \"> and recommend = #{recommend} </if></script>")
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
     * 保存博客
     * @param blog
     */
    @Insert("insert into t_blog values(#{id},#{appreciation},#{commentTable},#{content},#{createTime},#{firstPicture},#{flag},#{published},#{recommend},#{shareStatement},#{title},#{updateTime},#{views},#{type.id},#{user.id})")
    @SelectKey(keyColumn = "id",keyProperty = "id",before = false,resultType = Long.class,statement = "select last_insert_id()")
    void saveBlog(Blog blog);


    /**
     * 更新博客
     * @param blog
     */
    @Update("update t_blog set appreciation=#{appreciation},comment_table=#{commentTable},content=#{content},create_time=#{createTime},first_picture=#{firstPicture},flag=#{flag},published=#{published},recommend=#{recommend},share_statement=#{shareStatement},title=#{title},update_time=#{updateTime},views=#{views},type_id=#{type.id},user_id=#{user.id} where id=#{id}")
    void updateBlog(Blog blog);


    /**
     * 删除博客
     * @param id
     */
    @Delete("delete from t_blog where id = #{id}")
    void deleteBlog(Long id);

}
