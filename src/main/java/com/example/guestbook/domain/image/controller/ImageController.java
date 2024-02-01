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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

@RestController
@Log4j2
public class ImageController {
    @Value("${com.example.guestbook.path}")
    private String imagePath;



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
                    log.error(e.getMessage()); // 주의 (e.printStackTrace()) - 사용하지 말라는데 will be 없음
                }
                list.add(ImageResultDTO.builder().uuid(uuid).img(img).imgName(originalName).build());
            }); // end each
            return list;
        } // end if
        return null;
    }


    // 이미지 조회
    @GetMapping("/view/{imgName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String imgName){
        Resource resource = new FileSystemResource(imagePath + File.separator + imgName);

        String resourceName = resource.getFilename();
        log.info(resourceName);

        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));

        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().header(String.valueOf(headers)).body(resource);
    }

    @DeleteMapping("/remove/{imgName}")
    public Map<String, Boolean> removeFile(@PathVariable String imgName){
        Resource resource = new FileSystemResource(imagePath+File.separator+imgName);

        Map<String, Boolean> resultMap = new HashMap<>();
        boolean remove=false;

        try{
            String contentType = Files.probeContentType(resource.getFile().toPath());

            remove = resource.getFile().delete();

            if(contentType.startsWith("image")){
                File thumbnail = new File(imagePath+File.separator+"s_"+imgName);
                thumbnail.delete();
            }
        } catch (IOException e){
            log.error(e.getMessage());
        }
        resultMap.put("result", remove);
        return resultMap;
    }
}
