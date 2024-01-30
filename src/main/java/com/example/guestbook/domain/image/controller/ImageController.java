package com.example.guestbook.domain.image.controller;


import com.example.guestbook.domain.image.dto.ImageFileDTO;
import com.example.guestbook.domain.image.dto.ImageResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
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
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class ImageController {
    @Value("${com.example.guestbook.path}")
    private String imagePath;



//    @PostMapping("/upload")
//    public List<ImageResultDTO> upload(ImageFileDTO imageFileDTO){
//        log.info(imageFileDTO);
//
//        if(imageFileDTO.getFiles() != null){
//            final List<ImageResultDTO> list = new ArrayList<>();
//
//            imageFileDTO.getFiles().forEach(multipartFile -> {
//
//                String originalName = multipartFile.getOriginalFilename();
//                log.info(originalName);
//
//                String uuid = UUID.randomUUID().toString();
//                Path savePath = Paths.get(imagePath, uuid+"_"+originalName);
//
//                boolean images = false;
//
//                try{
//                    multipartFile.transferTo(savePath);
//
//                    // 섬네일 처리
//                    if(Files.probeContentType(savePath).startsWith("img")){
//                        File thumbnail = new File(imagePath, "S_"+uuid+originalName);
//
//                        Thumbnailator.createThumbnail(savePath.toFile(), thumbnail, 200, 200);
//
//                    }
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//
//                list.add(ImageResultDTO.builder().uuid(uuid).imgName(originalName).img(images).build());
//            }); // end each
//        } // end if
//        return null;
//    }
//    @PostMapping("/upload")
//    public List<ImageResultDTO> upload(ImageFileDTO imageFileDTO){
//        log.info(imageFileDTO);
//
////        imageFileDTO.getFiles().get().getOriginalFilename()
//        if(imageFileDTO != null){
//            final List<ImageResultDTO> list = new ArrayList<>();
//
//            imageFileDTO.getFiles().forEach(multiFile ->{
//                String originalName = multiFile.getOriginalFilename();
//                log.info("Original Name : "+originalName);
//
//                String uuid = UUID.randomUUID().toString();
//                Path savePaths = Paths.get(imagePath, originalName+File.separator+originalName);
//
//                boolean img = false;
//
//                try{
//                    multiFile.transferTo(savePaths);
//
//                    // 섬네일 - 이미지 경로
//                    if(Files.probeContentType(savePaths).startsWith("image")){
//                        img = true;
//                        File thumbnailFile = new File(imagePath, "s_"+uuid+"_"+originalName);
//                        Thumbnailator.createThumbnail(savePaths.toFile(), thumbnailFile, 200, 200);
//                    }
//
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//                list.add(ImageResultDTO.builder()
//                        .uuid(uuid)
//                        .imgName(originalName)
//                        .img(img)
//                        .build());
//            }); // end each
//            return list;
//        }// end if
//        return null;
//    }
    // 파일 업로드(ImageResultDTO 반환 및 섬네일 생성)
    @PostMapping("/upload")
    public List<ImageResultDTO> upload(ImageFileDTO imageFileDTO){
        log.info(imageFileDTO);

        if(imageFileDTO.getFiles() != null){
            final List<ImageResultDTO> list = new ArrayList<>();

            imageFileDTO.getFiles().forEach(multiFile->{
                String originalName = multiFile.getOriginalFilename();
                log.info(originalName);

                String uuid = UUID.randomUUID().toString();
                Path savePath = Paths.get(imagePath, uuid+"_"+originalName);

                boolean img = false;

                try{
                    multiFile.transferTo(savePath);

                    // 섬네일 생성
                    if(Files.probeContentType(savePath).startsWith("image")) {
                        img = true;

                        File thumbnail = new File(imagePath, "s_" + uuid + "_" + originalName);

                        Thumbnailator.createThumbnail(savePath.toFile(), thumbnail, 200, 200);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                list.add(ImageResultDTO.builder().uuid(uuid).img(img).imgName(originalName).build());
            }); // end each
            return list;
        } // end if
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
