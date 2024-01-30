package com.example.guestbook.domain.member.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class MemberDTO {

    // Member
    private Long mno;
    private String email;
    private String pwd;
    private String nickname;
    private String phone;

    // Review

}
