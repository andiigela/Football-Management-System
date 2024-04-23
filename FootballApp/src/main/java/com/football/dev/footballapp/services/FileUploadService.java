package com.football.dev.footballapp.services;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String uploadFile(String name,MultipartFile file);
    void deleteFile(String imagePath);
}
