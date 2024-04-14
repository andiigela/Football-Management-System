package com.football.dev.footballapp.services;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    Boolean uploadFile(MultipartFile multipartFile);
}
