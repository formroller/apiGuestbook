package com.example.guestbook.domain.image.controller;


import com.example.guestbook.domain.image.dto.ImageFileDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;

@RestController
@Log4j2
public class ImageController {
    @Value("${com.example.guestbook.path}")
    private String imagePath;


//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public String upload(ImageFileDTO imageFileDTO){
//
//        log.info(imageFileDTO);
//
//        if(imageFileDTO.getFiles()!=null){
//            imageFileDTO.getFiles().forEach(multipartFile -> {
//                log.info(multipartFile.getOriginalFilename());
//            }); // end for
//        } // end if
//
//        return null;
//    }

    @PostMapping("/upload")
    public String upload(ImageFileDTO imageFileDTO){
        log.info(imageFileDTO);
        return null;
    }


    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName){
        Resource resource = new FileSystemResource(imagePath + File.separator + fileName);

        String resourceName = resource.getFilename();

        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));

        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().header(String.valueOf(headers)).body(resource);
    }
}
