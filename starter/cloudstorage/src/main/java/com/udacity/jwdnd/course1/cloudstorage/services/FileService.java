package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.storage.FileException;
import com.udacity.jwdnd.course1.cloudstorage.storage.FileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {
    private FileMapper fileMapper;
    private UserService userService;

    private AuthenticationService authenticationService;

    private Map<String, String> allErrors;

    private final Path uploadLocation;

    @PostConstruct
    public void postConstructor(){
        this.allErrors = new HashMap<>();
    }
    public FileService(FileMapper fileMapper, UserService userService, AuthenticationService authenticationService, StorageProperties properties) {
        this.fileMapper = fileMapper;
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.uploadLocation = Paths.get(properties.getLocation());
    }

    public List<File> getAllFiles(){
        return fileMapper.getAllFiles();
    }

    public Map<String, String> getAllErrors() {
        return allErrors;
    }

    public void addError(String errorCode, String errorMessage) {
        allErrors.put(errorCode, errorMessage);
    }

    public void clearAllErrors(){
        allErrors.clear();
    }

    public int addFileToDB(MultipartFile file){
        File newFile = new File(file.getOriginalFilename());
        newFile.setContenttype(file.getContentType());
//        try {
//            newFile.setFiledata(file.getInputStream().toString());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        newFile.setFilesize(String.valueOf(file.getSize()));
        User user = userService.getUser(authenticationService.getAuthentication().getName());
        if(user == null){
            addError("LOGIN_ERROR", "You must logout and login again!");
            return 0;
        }
        newFile.setUserid(user.getUserId());
        return fileMapper.insert(newFile);
    }

    public void storeFile(MultipartFile file) {
        User user = userService.getUser(authenticationService.getAuthentication().getName());
        if(user == null){
            addError("LOGIN_ERROR", "You must logout and login again!");
            return;
        }
        try {
            if (file.isEmpty()) {
                addError("FILE_NOT_SELECTED_ERROR","Please choose file!");
                return;
//                throw new FileException("Failed to store empty file.");
            }
            Path destinationFile = this.uploadLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.uploadLocation.toAbsolutePath())) {
                addError("INVALID_UPLOAD_LOCATION_ERROR","Cannot store file outside current directory.");
                return;
                // This is a security check
//                throw new FileException(
//                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            addError("UPLOAD_FILE_ERROR","Failed to upload file.");
            throw new FileException("Failed to store file.", e);
        }

//        newFile.setUserid(userService.getUser("1").getUserId());
        addFileToDB(file);

    }

    public boolean isExistsFile(String fileName){
        return fileMapper.getFile(fileName) != null;
    }

    public Path load(String filename) {
        return uploadLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + filename, e);
        }
    }
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(uploadLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectories(uploadLocation);
        }
        catch (IOException e) {
            throw new FileException("Could not initialize storage", e);
        }
    }
}
