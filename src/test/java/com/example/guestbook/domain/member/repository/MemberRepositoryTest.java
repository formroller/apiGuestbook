package com.example.guestbook.domain.member.repository;

import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.entity.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    public void insertMember(){
        IntStream.rangeClosed(1,300).forEach(i->{
            Random random = new Random();
            int firstNum = random.nextInt(1000,9999);
            int secNum = random.nextInt(1000,9999);

            Member member = Member.builder()
                    .email("aa"+i+"@aa.com")
                    .pwd("1111")
//                    .phone("010-"+firstNum+"-"+secNum)
                    .build();

            repository.save(member);
        });
    }

    @Test
    void getWithRoles() {
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder()
                    .mid("member"+i)
                    .pwd(passwordEncoder.encode("1111"))
                    .email("aa"+i+"@.com")
                    .build();

            member.addRole(MemberRole.USER);{
                if(i>=90){
                    member.addRole(MemberRole.ADMIN);
                }
                repository.save(member);
            }
        });
    }

    @DisplayName("회원 조회")
    @Test
    public void testRead(){
        Optional<Member> result = repository.getWithRoles("member32");

        Member member = result.orElseThrow();

        System.out.println(member);
        System.out.println(member.getRoleSet());

        member.getRoleSet().forEach(roles -> System.out.println(roles.name()));

    }
}