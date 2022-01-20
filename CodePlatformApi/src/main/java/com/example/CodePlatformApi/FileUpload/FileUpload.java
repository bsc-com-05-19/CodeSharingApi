package com.example.CodePlatformApi.FileUpload;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fileUpload")
public class FileUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 512, nullable = false, unique = true)
    private String filename;
    private long size;

    @Column(name = "uploadTime")
    private Date uploadTime;

    private byte[] content;

    public FileUpload(Long id, String filename, long size) {
        this.id = id;
        this.filename = filename;
        this.size = size;
    }

    public FileUpload() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
