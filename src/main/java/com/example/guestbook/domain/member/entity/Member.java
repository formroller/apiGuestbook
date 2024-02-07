package com.example.guestbook.domain.member.entity;

import com.example.guestbook.global.auditable.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString(exclude = "roleSet")
public class Member extends Auditable {
    @Id
    private String mid;
    private String email;
    private String pwd;

    private boolean del; // 삭제 여부
    private boolean social;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void changePwd(String pwd){this.pwd=pwd;}
    public void changeEmail(String email){this.email=email;}

    public void changeDel(boolean del){this.del=del;}

    public void addRole(MemberRole memberRole){this.roleSet.add(memberRole);}

    public void changeSocial(boolean social){this.social=social;}


}
