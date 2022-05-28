package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.storage.FileException;
import com.udacity.jwdnd.course1.cloudstorage.storage.FileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final Path uploadLocation;
    private final UserService userService;

    public FileService(FileMapper fileMapper, StorageProperties properties, UserService userService) {
        this.fileMapper = fileMapper;
        this.uploadLocation = Paths.get(properties.getLocation());
        this.userService = userService;
    }

    public List<File> getAllFiles(){
        return fileMapper.getAllFiles();
    }

    public int addFileToDB(Authentication authentication, MultipartFile multipartFile){
        File newFile = new File(multipartFile.getOriginalFilename());
        newFile.setContenttype(multipartFile.getContentType());
        try {
            newFile.setFiledata(multipartFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        newFile.setFilesize(String.valueOf(multipartFile.getSize()));
        newFile.setUserid(userService.getUser(authentication.getName()).getUserId());
        return fileMapper.insert(newFile);
    }

    public void storeFileUploadLocation(MultipartFile multipartFile) {
        try {
            Path destinationFile = this.uploadLocation.resolve(
                            Paths.get(Objects.requireNonNull(multipartFile.getOriginalFilename())))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.uploadLocation.toAbsolutePath())) {
                // This is a security check
                throw new FileException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new FileException("Failed to store file.", e);
        }
    }

    public void uploadFile(Authentication authentication, MultipartFile multipartFile) {
        storeFileUploadLocation(multipartFile);
        addFileToDB(authentication, multipartFile);
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

    public void deleteFile(String fileName) {
        fileMapper.delete(fileName);
    }
}
