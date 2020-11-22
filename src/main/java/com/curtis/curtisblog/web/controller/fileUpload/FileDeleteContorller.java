package com.curtis.curtisblog.web.controller.fileUpload;

import com.curtis.curtisblog.upload.utils.ImagesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileDeleteContorller {

    @Autowired
    private ImagesUtils imagesUtils;

    @PostMapping("/delete")
    public boolean upload(String fileUrl){
        return this.imagesUtils.deleteFile(fileUrl);
    }
}
