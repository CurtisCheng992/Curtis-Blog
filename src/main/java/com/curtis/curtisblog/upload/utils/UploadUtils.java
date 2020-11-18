package com.curtis.curtisblog.upload.utils;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class UploadUtils {

    @Autowired
    private FastFileStorageClient storageClient;

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

            // 生成url地址
            return  "http://182.254.148.75/" + storePath.getFullPath();

        } catch (IOException e) {
            return null;
        }

    }
}