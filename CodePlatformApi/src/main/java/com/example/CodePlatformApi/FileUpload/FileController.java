package com.example.CodePlatformApi.FileUpload;

import com.example.CodePlatformApi.Registration.RegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/registration")
public class FileController {
    @Autowired
    private FileRepository repository;

    @GetMapping(path = "/api/v1/registration/")
    public String ViewHome(Model model) {
        List<FileUpload> fileUploadList = repository.findAll();
        model.addAttribute("fileUploadList", fileUploadList);
        return "home";
    }
    @Operation(summary = "api/v1/registration/upload", description = "upload files to database", tags = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "file uploaded successfully",

                    content = {@Content(mediaType = "application/json",

                            schema = @Schema(implementation = RegistrationRequest.class))}),
            @ApiResponse(responseCode = "404", description = "file not uploaded",
                    content = @Content)
    })

    @PostMapping(path = "upload")
    public String uploadFile(@RequestParam("FileUpload") MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        FileUpload fileUpload = new FileUpload();
        fileUpload.setFilename(fileName);
        fileUpload.setContent(multipartFile.getBytes());
        fileUpload.setSize(multipartFile.getSize());
        fileUpload.setUploadTime(new Date());

        repository.save(fileUpload);
        ra.addFlashAttribute("message", "The File Has Been Uploaded Successfully");

        return "redirect:/";
    }
    @Operation(summary = "api/v1/registration/download", description = "download files from database", tags = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description = "SUCCESS",

                    content = {@Content(mediaType = "application/json",

                            schema = @Schema(implementation = RegistrationRequest.class))}),
            @ApiResponse(responseCode = "404", description = "couldn't find document with ID",
                    content = @Content)
    })

    @GetMapping("download")
    public void downloadFile(@Param("id") Long id, HttpServletResponse response) throws Exception {
        Optional<FileUpload> results = repository.findById(id);
        if (!results.isPresent()) {
            throw new Exception("couldn't find document with ID " + id);
        }
        FileUpload fileUpload = results.get();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename" + fileUpload.getFilename();

        response.setHeader(headerKey, headerValue);
        ServletOutputStream OutputStream = response.getOutputStream();

        OutputStream.write(fileUpload.getContent());
        OutputStream.close();
    }

}
