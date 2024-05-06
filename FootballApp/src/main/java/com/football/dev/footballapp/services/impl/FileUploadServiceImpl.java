package com.football.dev.footballapp.services.impl;
import com.football.dev.footballapp.services.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${upload_dir}")
    private String uploadDir;
    @Override
    public String uploadFile(String name,MultipartFile file) {
        if(file.isEmpty() || file.getOriginalFilename() == null) return null;
        String splittedOriginalFileName[] = StringUtils.cleanPath(file.getOriginalFilename()).split("\\.");
        if(splittedOriginalFileName.length > 0){
            String filename = this.constructFilename(name,file,splittedOriginalFileName);
            Path targetLocation = Paths.get(uploadDir).resolve(filename); // bashkon /static/images + "emri_file.txt";
            try {
                Files.copy(file.getInputStream(),targetLocation); // e run "emri_file.txt" ne qat directory
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return filename;
        }
        return null;
    }
    public String constructFilename(String name,MultipartFile file,String[] splittedOriginalFileName){
        String filenameWithoutExtension = StringUtils.stripFilenameExtension(file.getOriginalFilename()).replace(" ","-");
        String filename = filenameWithoutExtension + "-" + name.replace(" ","-") + "-" + UUID.randomUUID() + "." + splittedOriginalFileName[splittedOriginalFileName.length-1];
        return filename;
    }
    @Override
    public void deleteFile(String imagePath) {
        if(!imagePath.trim().isEmpty() && imagePath != null){
            Path targetLocation = Paths.get(uploadDir).resolve(imagePath); // bashkon /static/images + "emri_file.txt";
            try {
                Files.deleteIfExists(targetLocation);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
