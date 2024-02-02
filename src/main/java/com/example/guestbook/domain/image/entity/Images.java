package com.example.guestbook.domain.image.entity;

import com.example.guestbook.domain.guestbook.entity.Guestbook;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString(exclude = "guestbook")
public class Images implements Comparable<Images>{
    @Id
    private String uuid;

    private String imgName;

    private String path;

    private int ord;

    @ManyToOne(fetch = FetchType.LAZY)
    private Guestbook guestbook;


    @Override
    public int compareTo(Images order) {
        return this.ord - order.ord;
    }

    public void changeGuestbook(Guestbook guestbook){
        this.guestbook = guestbook;
    }
}
