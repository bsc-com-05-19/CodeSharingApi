package com.example.CodePlatformApi.fileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Date;

@RestController
public class FileUploadController {

    @Autowired
    private FileUploadRepository repo;

    @GetMapping(path = "/api/v1/registration/")
    public String ViewHome(){
        return "home";
    }

    @PostMapping(path = "/api/v1/registration/upload")
    public String uploadFile(@RequestParam("FileUpload")MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        FileUpload fileUpload = new FileUpload();
        fileUpload.setFilename(fileName);
        fileUpload.setContent(multipartFile.getBytes());
        fileUpload.setSize(multipartFile.getSize());
        fileUpload.setUploadTime(new Date());

        repo.save(fileUpload);
        ra.addFlashAttribute("message", "The File Has Been Uploaded Successfully");

        return "redirect:/";
     }
    }
