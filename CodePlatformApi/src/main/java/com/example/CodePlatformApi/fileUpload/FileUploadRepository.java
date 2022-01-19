package com.example.CodePlatformApi.fileUpload;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {

   @Override
    @Query("SELECT NEW FileUpload (e.id, e.filename, e.size) FROM  FileUpload e ORDER BY e.uploadTime DESC ")
    List<FileUpload> findAll();

}
