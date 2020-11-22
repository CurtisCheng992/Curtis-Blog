package com.curtis.curtisblog.mapper;

import com.curtis.curtisblog.entity.Image;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * 图片的mapper
 */
@Mapper
public interface ImageMapper {

    @Results(id = "imageMap",
            value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "link", property = "link"),
            @Result(column = "description", property = "description"),
            @Result(column = "file_name", property = "fileName"),
            @Result(column = "file_size", property = "fileSize"),
            @Result(column = "upload_time", property = "uploadTime"),
            @Result(column = "update_time", property = "updateTime"),
            })
    @Select("select * from t_images")
    List<Image> listAllImages();

    @Delete("delete from t_images where id = #{id}")
    void deleteById(Long id);

    @ResultMap("imageMap")
    @Select("select * from t_images where id = #{id}")
    Image findById(Long id);

    @ResultMap("imageMap")
    @Select("select * from t_images where link = #{link}")
    Image findByLink(String link);

    @Insert("insert into t_images(link,description,file_name,file_size,upload_time,update_time) values(#{image.link},#{image.description},#{image.fileName},#{image.fileSize},#{image.uploadTime},#{image.updateTime})")
    void saveImage(@Param("image") Image image);

    @Update("update t_images set description = #{description},update_time = #{updateTime} where id = #{id}")
    void updateImagesDescriptionById(@Param("id") Long id, @Param("description") String description, @Param("updateTime")Date updateTime);
}
