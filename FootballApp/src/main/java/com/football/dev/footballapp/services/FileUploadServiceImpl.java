package com.football.dev.footballapp.services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${upload_dir}")
    private String uploadDir;
    @Override
    public Boolean uploadFile(MultipartFile file) {
        if(file.isEmpty() || file.getOriginalFilename() == null) return false;
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = Paths.get(uploadDir).resolve(filename); // bashkon /static/images + "emri_file.txt";
        try {
            Files.copy(file.getInputStream(),targetLocation); // e run "emri_file.txt" ne qat directory
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
