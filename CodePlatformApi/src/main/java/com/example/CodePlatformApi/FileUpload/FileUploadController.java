package com.example.CodePlatformApi.FileUpload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
public class FileUploadController {

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;

    @PostMapping(value = "/api/v1/registration/uploadFile")
    public ResponseEntity<Object> fileUpload(@RequestParam("File") MultipartFile file ) throws IOException {
        File Myfile = new File(FILE_DIRECTORY+file.getOriginalFilename());
        Myfile.createNewFile();
        FileOutputStream fos = new FileOutputStream(Myfile);
        fos.write(file.getBytes());
        fos.close();
        return  new ResponseEntity<Object>("The File Uploaded Successfully", HttpStatus.OK);
    }
}
