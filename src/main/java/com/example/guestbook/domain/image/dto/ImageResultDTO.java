package com.example.guestbook.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResultDTO {
    private String uuid;
    private String imgName;
    private boolean img;

    public String getLink(){ // json 처리시 link 속성 처리됨
        if(img){
            return "s_"+uuid+imgName; // 이미지일때 섬네일
        }else{
            return uuid+imgName;
        }
    }
}
