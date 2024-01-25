package com.example.guestbook.domain.member.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberDTO {

//    // Guestbook
//    private Long gno;
//    private String title;
//    private String content;
//    private String modDate, regDate;

    // Member
    private Long mno;

    private String mid;

    private String pwd;
    private String nickname;
    private String email;
    private String phone;

    // Review

}
