package com.example.CodePlatformApi.fileUpload;

import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class FileUploadController {

    private FileUploadRepository repo;

    @GetMapping(path = "/api/v1/registration/home")
    public String ViewHome(Model model){
        List<FileUpload> fileUploadList  = repo.findAll();
        model.addAttribute("fileUploadList", fileUploadList);
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
     @GetMapping("/api/v1/registration/download")
    public void downloadFile(@Param("id") Long id, HttpServletResponse response) throws Exception {
        Optional<FileUpload> results = repo.findById(id);
        if(!results.isPresent()){
            throw new Exception("couldn't find document with ID " + id);
        }
        FileUpload fileUpload = results.get();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename" + fileUpload.getFilename();

        response.setHeader(headerKey, headerValue);
          ServletOutputStream OutputStream =  response.getOutputStream();

          OutputStream.write(fileUpload.getContent());
          OutputStream.close();
     }
    }
