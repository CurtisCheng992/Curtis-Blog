package com.curtis.curtisblog.web.controller.fileUpload;

import com.curtis.curtisblog.upload.utils.ImagesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    @Autowired
    private ImagesUtils imagesUtils;

    @PostMapping("/upload")
    public String upload(MultipartFile file){
        return this.imagesUtils.upload(file);
    }

}
