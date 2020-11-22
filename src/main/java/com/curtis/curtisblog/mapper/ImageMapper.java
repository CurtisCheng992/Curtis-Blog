package com.curtis.curtisblog.mapper;

import com.curtis.curtisblog.entity.Image;
import org.apache.ibatis.annotations.*;

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

    @Insert("insert into t_images(link,description,file_name) values(#{image.link},#{image.description},#{image.fileName})")
    void saveImage(@Param("image") Image image);

    @Update("update t_images set description = #{description} where id = #{id}")
    void updateImagesDescriptionById(@Param("id") Long id, @Param("description") String description);
}
