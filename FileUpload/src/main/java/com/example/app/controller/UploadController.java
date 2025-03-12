package com.example.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UploadController {
    private static final String UPLOAD_DIRECTORY = "C:/Users/zd3N06/gallery/";
    @GetMapping("/")
    public String index() {
        return "redirect:/upload";
    }
    @GetMapping("/upload")
    public String showImages(Model model){
        File uploadDirectory = new File(UPLOAD_DIRECTORY);
        File[] fileList = uploadDirectory.listFiles();
        List<String> fileNames = Arrays.stream(fileList).map(file -> file.getName()).toList();
        model.addAttribute("fileNames", fileNames);
        return "upload";
    }
    @PostMapping("/upload")
    public String uploadImage(@RequestParam MultipartFile upfile,Model model) throws Exception {
        if (upfile.isEmpty()) {
            return "redirect:/upload";
        }
        if(!upfile.getContentType().startsWith("image/")) {
            return "redirect:/upload";
        }
        String fileName = upfile.getOriginalFilename();
        System.out.println("FileName: " + fileName);

        File dest = new File(UPLOAD_DIRECTORY + "/" + fileName);
        upfile.transferTo(dest);
        model.addAttribute("fileName", fileName);
        return "uploadDone";
    }
    @GetMapping("/delete/{fileName}")
    public String deleteImage(@PathVariable String fileName) throws IOException {
        Path path = Paths.get(UPLOAD_DIRECTORY + "/" + fileName);
        Files.delete(path);
        return "redirect:/upload";
    }     
    
}
