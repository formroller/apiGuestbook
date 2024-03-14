package com.example.guestbook.domain.guestbook.repository;

import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookRepositoryTest {
    @Autowired
    private GuestbookRepository repository;

    @DisplayName("저장 테스트")
    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{

//            Member member =
            Guestbook guestbook = Guestbook.builder()
                    .gno((long)i)
                    .title("Title +++ "+i)
                    .content("Content +++ "+i)
                    .writer("aa"+i+"@aa.com")
                    .build();

            repository.save(guestbook);
        });
    }

}