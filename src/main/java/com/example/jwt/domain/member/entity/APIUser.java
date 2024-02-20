package com.example.jwt.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class APIUser {
    @Id
    private String mid;
    private String pwd;

    public void changePwd(String pwd){this.pwd=pwd;}
}
