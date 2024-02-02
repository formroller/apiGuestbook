package com.example.guestbook.domain.member.repository;

import com.example.guestbook.domain.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository repository;


    @Test
    public void insertMember(){
        IntStream.rangeClosed(1,300).forEach(i->{
            Random random = new Random();
            int firstNum = random.nextInt(1000,9999);
            int secNum = random.nextInt(1000,9999);

            Member member = Member.builder()
                    .email("aa"+i+"@aa.com")
                    .pwd("1111")
                    .nickname("user"+i)
                    .phone("010-"+firstNum+"-"+secNum)
                    .build();

            repository.save(member);
        });
    }

}