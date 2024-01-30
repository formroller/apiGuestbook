package com.example.guestbook.domain.image.repository;

import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.guestbook.repository.GuestbookRepository;
import com.example.guestbook.domain.image.entity.Images;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageRepositoryTest {
    @Autowired
    private GuestbookRepository guestbookRepository;
    @Autowired
    private ImageRepository repository;

    @Test
    public void insertImages(){
        IntStream.rangeClosed(1,100).forEach(i->{
//            Guestbook guestbook = Guestbook.builder().gno((long)i).build();
            Guestbook guestbook = Guestbook.builder().title("Title Test "+i).build();

            guestbookRepository.save(guestbook);

            Images images = Images.builder()
                    .uuid(UUID.randomUUID().toString())
                    .guestbook(guestbook)
                    .imgName("test"+i+".jpg")
                    .build();

            repository.save(images);
        });
    }

}