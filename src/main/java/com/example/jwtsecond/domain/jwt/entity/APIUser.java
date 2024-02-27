package com.example.jwtsecond.domain.jwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class APIUser {
    @Id
    private String mid;
    private String pwd;

    public void changePwd(){this.pwd=pwd;}
}
