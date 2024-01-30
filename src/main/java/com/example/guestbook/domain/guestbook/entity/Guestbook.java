package com.example.guestbook.domain.guestbook.entity;


import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.global.auditable.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    public void changeTitle(String title){this.title=title;}
    public void changeContent(String content){this.content=content;}


}
