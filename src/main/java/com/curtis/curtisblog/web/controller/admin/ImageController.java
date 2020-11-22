package com.curtis.curtisblog.web.controller.admin;

import com.curtis.curtisblog.entity.Image;
import com.curtis.curtisblog.service.IImagesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 图片的控制器
 */
@Controller
@RequestMapping("/admin")
public class ImageController {

    @Autowired
    private IImagesService iImagesService;

    @GetMapping("/images")
    public String images(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum, Model model){
        model.addAttribute("pageInfo",this.iImagesService.getImagePage(pageNum,5));
        return "admin/images";
    }

    @GetMapping("/images/upload")
    public String imagesUpload(){
        return "admin/images-upload";
    }

    @GetMapping("/images/{id}/edit")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("image",iImagesService.getImageById(id));
        return "admin/images-edit";
    }

    @GetMapping("/images/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        this.iImagesService.deleteImageById(id);
        attributes.addFlashAttribute("message","删除图片成功！");
        return "redirect:/admin/images";
    }

    @PostMapping("/images/upload")
    public String upload(@RequestParam("file") MultipartFile file,@RequestParam("description") String description, RedirectAttributes attributes,Model model){
        String link = this.iImagesService.uploadImage(file, description);
        Image imageByLink = this.iImagesService.findImageByLink(link);
        if (imageByLink != null){
            attributes.addFlashAttribute("message","上传图片成功！");
        }else {
            attributes.addFlashAttribute("message","上传图片失败！");
        }
        model.addAttribute("image",imageByLink);
        return "redirect:/admin/images";
    }

    @PostMapping("/images/{id}/edit")
    public String edit(@PathVariable Long id, @RequestParam("description") String description , RedirectAttributes attributes){
        this.iImagesService.updateImagesDescription(id,description);
        Image imageById = this.iImagesService.getImageById(id);
        if (StringUtils.equals(imageById.getDescription(),description)){
            attributes.addFlashAttribute("message","修改图片描述成功！");
        }else {
            attributes.addFlashAttribute("message","修改图片描述失败！");
        }
        return "redirect:/admin/images";
    }
}
