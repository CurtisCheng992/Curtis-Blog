package com.curtis.curtisblog.service;

import com.curtis.curtisblog.entity.Image;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 评论的业务层接口
 */
public interface IImagesService {

    /**
     * 查询所有图片信息
     * @return
     */
    List<Image> listAllImages();

    /**
     * 分页查询图片信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Image> getImagePage(int pageNum, int pageSize);

    /**
     * 根据图片id删除图片信息
     * @param id 图片id
     */
    void deleteImageById(Long id);

    /**
     * 根据id查询图片信息
     * @param id
     * @return
     */
    Image getImageById(Long id);

    /**
     * 上传图片
     * @return
     */
    String uploadImage(MultipartFile file, String description);

    /**
     * 根据图片链接查询图片
     * @param link 图片链接
     */
    Image findImageByLink(String link);

    /**
     * 根据图片id修改图片描述
     * @param id            图片id
     * @param description   图片描述
     */
    void updateImagesDescription(Long id, String description);
}
