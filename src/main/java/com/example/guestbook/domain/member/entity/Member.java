package com.example.guestbook.domain.member.entity;

import com.example.guestbook.global.auditable.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;
    private String email;
    private String pwd;
    private String nickname;
    private String phone;

}
