package com.example.guestbook.domain.review.entity;

import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.global.auditable.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"guestbook","member"})
public class Review extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;
    
    private String reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Guestbook guestbook;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
