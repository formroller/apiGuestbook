package com.example.guestbook.domain.guestbook.entity;


import com.example.guestbook.domain.image.entity.Images;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.global.auditable.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "writer")
@Getter
public class Guestbook extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    @NotNull
    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Member writer;


    public void change(String title, String content){
        this.title=title;
        this.content=content;
    }

    @OneToMany(mappedBy = "guestbook",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Images> imageSet = new HashSet<>();

    // 하위 객체 관리 (Image)
    public void addImage(String uuid, String imgName){
        Images images = Images.builder()
                .uuid(uuid)
                .imgName(imgName)
                .guestbook(this)
                .ord(imageSet.size())
                .build();
        imageSet.add(images);
    }

    public void clearImages(){
        imageSet.forEach(image -> image.getGuestbook().change(null, null));
        this.imageSet.clear();
    }


}
