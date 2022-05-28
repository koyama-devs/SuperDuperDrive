package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.storage.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class UploadFileController {

    private final FileService fileService;

    @Autowired
    public UploadFileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) {
        model.addAttribute("files", fileService.getAllFiles());
        return "home";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = fileService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String storeFileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, RedirectAttributes redirectAttributes){
        if(fileService.isExistsFile(fileUpload.getOriginalFilename())){
            redirectAttributes.addFlashAttribute("errorMessage", "Can not to upload two files with the same name!");
            return "redirect:/?error";
        }

        fileService.clearAllErrors();
        fileService.storeFile(fileUpload);
        redirectAttributes.addFlashAttribute("errorMessageList", fileService.getAllErrors());
        return "redirect:/";
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound() {
        return ResponseEntity.notFound().build();
    }
}
