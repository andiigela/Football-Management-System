package com.football.dev.footballapp.services;

import com.football.dev.footballapp.dto.UserEntityDto;
import com.football.dev.footballapp.exceptions.UserNotFoundException;
import com.football.dev.footballapp.models.Club;
import com.football.dev.footballapp.models.UserEntity;
import com.football.dev.footballapp.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final Function<UserEntityDto, UserEntity> userEntityDtoToUserEntity;
    private final FileUploadService fileUploadService;
    public UserServiceImpl(UserRepository userRepository, Function<UserEntityDto, UserEntity> userEntityDtoToUserEntity,
            FileUploadService fileUploadService){
        this.userRepository=userRepository;
        this.userEntityDtoToUserEntity = userEntityDtoToUserEntity;
        this.fileUploadService=fileUploadService;
    }
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public void updateUserStatus(Long userId, boolean enabled) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(enabled);
        userRepository.save(user);
    }
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public List<UserEntity> getUsersByRole(String role) {
        return userRepository.findByRoleDescription(role);
    }
    @Override
    public void saveUser(UserEntityDto userDto) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInUserEmail = authentication.getName();

            UserEntity loggedInUser = userRepository.findByEmail(loggedInUserEmail)
                    .orElseThrow(() -> new RuntimeException("Logged-in user not found"));


            UserEntity userEntity = userEntityDtoToUserEntity.apply(userDto);

            userEntity.setId(loggedInUser.getId());
            userEntity.setEmail(loggedInUserEmail);
            userEntity.setPassword(loggedInUser.getPassword());
            userEntity.setRole(loggedInUser.getRole());

            userRepository.save(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save user: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateUser(Long userId, UserEntityDto updatedUserDto, MultipartFile file) {
        if(updatedUserDto == null) return;
        UserEntity userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (file != null && !file.isEmpty()) {
            // A new file is provided, handle file upload
            // fileUploadService.deleteFile(userToUpdate.getProfile_picture());Deleting previous file
            String fileUpload = fileUploadService.uploadFile(userToUpdate.getFirstName(), file);
            if (fileUpload == null) {
                throw new RuntimeException("Failed to upload file.");
            }
            userToUpdate.setProfile_picture(fileUpload); // Update imagePath with new file path
        }
        userToUpdate.setFirstName(updatedUserDto.getFirstName());
        userToUpdate.setLastName(updatedUserDto.getLastName());
        userToUpdate.setPhone(updatedUserDto.getPhone());
        userToUpdate.setCountry(updatedUserDto.getCountry());
        userToUpdate.setBirthDate(updatedUserDto.getBirthDate());
        userToUpdate.setAddress(updatedUserDto.getAddress());
        userToUpdate.setCity(updatedUserDto.getCity());
        userToUpdate.setPostal_code(updatedUserDto.getPostal_code());
        userToUpdate.setGender(updatedUserDto.getGender());

        userRepository.save(userToUpdate);
    }

    @Override
    public UserEntity getUserProfile(Long userId) {
        if(userId == null || userId < 0) throw new IllegalArgumentException("User id must be a positive non-zero value");
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserEntity> getUsersByRoleAndIsDeleted(String role, boolean isDeleted) {
        return userRepository.findByRoleDescriptionAndIsDeleted(role, isDeleted);
    }
}
