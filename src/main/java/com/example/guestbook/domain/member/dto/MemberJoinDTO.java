package com.example.guestbook.domain.member.dto;

import lombok.Data;

@Data
public class MemberJoinDTO {
    private String mid;
    private String pwd;
    private String email;
    private boolean del;
    private boolean social;
}
