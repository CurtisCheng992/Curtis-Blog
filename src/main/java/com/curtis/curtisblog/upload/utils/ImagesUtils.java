package com.curtis.curtisblog.upload.utils;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class ImagesUtils {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    // 支持的文件类型：.jpg .jpeg .png .mp4 .avi .doc .xls .pdf .ppt .txt
    private static final List<String> CONTENT_TYPES = Arrays.asList( "image/jpeg", "image/png", "video/mpeg4",
            "video/avi", "application/msword", "application/x-xls", "application/pdf", "application/x-ppt", "text/plain" );


    public String upload(MultipartFile file) {

        if (file == null) {
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        // 校验文件的类型
        String contentType = file.getContentType();
        if (!CONTENT_TYPES.contains( contentType )) {
            // 文件类型不合法
            return null;
        }

        try {
            // 保存到服务器
            String ext = StringUtils.substringAfterLast( originalFilename, "." );

            StorePath storePath = this.storageClient.uploadFile( file.getInputStream(), file.getSize(), ext, null );

            System.out.println("http://182.254.148.75/" + storePath.getFullPath());

            //生成文件大小
            long fileSize = file.getSize();
            // 首先先将.getSize()获取的Long转为String 然后将String转为Float并除以1024 （因为1KB=1024B）
            Float size = Float.parseFloat(String.valueOf(fileSize)) / 1024;
            BigDecimal b = new BigDecimal(size);
            // 2表示2位 ROUND_HALF_UP表明四舍五入，
            size = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            // 此时size就是保留两位小数的浮点数

            // 生成url地址
            String url = "http://182.254.148.75/" + storePath.getFullPath();
            String result = url + "@" + originalFilename + "@" +size + " KB";
            return  result;

        } catch (IOException e) {
            return null;
        }

    }

    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return boolean
     */
    public boolean deleteFile(String fileUrl){
        boolean flag = false;
        if (StringUtils.isEmpty(fileUrl)) {
            return flag;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            flag = true;
        } catch (FdfsUnsupportStorePathException e) {
            return false;
        }
        return flag;
    }
}
