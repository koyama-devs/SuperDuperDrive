package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.storage.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = fileService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, RedirectAttributes redirectAttributes, Authentication authentication){
        HomeController.activeTab = "Files";

        // Validate upload file
        if (fileUpload.isEmpty()) {
            redirectAttributes.addFlashAttribute("fileUploadErrMsg", "Please choose file!");
            return "redirect:/";
        }
        if(fileService.isExistsFile(fileUpload.getOriginalFilename())){
            redirectAttributes.addFlashAttribute("fileUploadErrMsg", "Can not upload two files with the same name!");
            return "redirect:/";
        }

        fileService.uploadFile(authentication, fileUpload);
        return "redirect:/";
    }

    @GetMapping("/delete-file/{fileName}")
    public String deleteFile(@PathVariable String fileName) {
        fileService.deleteFile(fileName);
        HomeController.activeTab = "Files";
        return "redirect:/";
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound() {
        return ResponseEntity.notFound().build();
    }
}
