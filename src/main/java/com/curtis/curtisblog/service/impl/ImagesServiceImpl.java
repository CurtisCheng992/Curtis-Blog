package com.curtis.curtisblog.service.impl;

import com.curtis.curtisblog.entity.Image;
import com.curtis.curtisblog.mapper.ImageMapper;
import com.curtis.curtisblog.service.IImagesService;
import com.curtis.curtisblog.upload.utils.ImagesUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 评论的业务层接口实现类
 */
@Service
public class ImagesServiceImpl implements IImagesService {

    @Autowired
    private ImagesUtils imagesUtils;

    @Autowired
    private ImageMapper imageMapper;

    /**
     * 查询所有图片信息
     * @return
     */
    @Override
    public List<Image> listAllImages() {
        return this.imageMapper.listAllImages();
    }

    /**
     * 分页查询类型
     * @return
     */
    @Override
    public PageInfo<Image> getImagePage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Image> images = imageMapper.listAllImages();
        PageInfo<Image> imagePageInfo = new PageInfo<>(images);
        return imagePageInfo;
    }

    /**
     * 根据图片id删除图片信息
     * @param id 图片id
     */
    @Transactional
    @Override
    public void deleteImageById(Long id) {
        Image image = this.imageMapper.findById(id);
        this.imagesUtils.deleteFile(image.getLink());
        this.imageMapper.deleteById(id);
    }

    /**
     * 根据图片id查询图片信息
     * @param id
     * @return
     */
    @Override
    public Image getImageById(Long id) {
        return this.imageMapper.findById(id);
    }

    /**
     * 上传图片
     * @param file
     * @param description
     * @return
     */
    @Transactional
    @Override
    public String uploadImage(MultipartFile file, String description) {
        String result = this.imagesUtils.upload(file);
        String[] split = result.split("@");
        String link = split[0];
        String fileName = split[1];
        String fileSize = split[2];
        Image image = new Image();
        image.setLink(link);
        image.setDescription(description);
        image.setFileName(fileName);
        image.setUploadTime(new Date());
        image.setUpdateTime(new Date());
        image.setFileSize(fileSize);
        this.imageMapper.saveImage(image);
        return link;
    }

    /**
     * 根据图片链接查询图片
     * @param link 图片链接
     */
    @Override
    public Image findImageByLink(String link) {
        return this.imageMapper.findByLink(link);
    }

    /**
     * 根据图片id修改图片描述
     * @param id            图片id
     * @param description   图片描述
     */
    @Transactional
    @Override
    public void updateImagesDescription(Long id, String description) {
        Date updateTime = new Date();
        this.imageMapper.updateImagesDescriptionById(id,description,updateTime);
    }
}
