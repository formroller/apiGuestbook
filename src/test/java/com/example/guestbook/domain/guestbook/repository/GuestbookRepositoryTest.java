package com.example.guestbook.domain.guestbook.repository;

import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.image.repository.ImageRepository;
import com.example.guestbook.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class GuestbookRepositoryTest {
    @Autowired
    private GuestbookRepository repository;
    @Autowired
    private ImageRepository imageRepository;

    @DisplayName("저장 테스트")
    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{

            Member member = Member.builder().email("aa"+i+"@aa.com").build();
            Guestbook guestbook = Guestbook.builder()
                    .title("(New) Title +++ "+i)
                    .content("Content +++ "+i)
                    .writer(member)
                    .build();

            repository.save(guestbook);
        });
    }

    @Transactional
    @Test
    public void testRead(){
        Optional<Guestbook> result = repository.findById(11L);

        Guestbook guestbook = result.get();

        System.out.println(guestbook);
        System.out.println(guestbook.getWriter());

    }

    // 조회
//    @Test
//    public void testReadWithWriter(){
//        Object result = repository.getGuestbookWithWriter(98L);
//
//        Object[] arr = (Object[]) result;
//
//        System.out.println(Arrays.toString(arr));
////        Object result = repository.findById(11L);
////        Object[] arr = (Object[]) result;
////
////        System.out.println(Arrays.toString(arr));
//    }

    @Test
    public void testReadWithWriter() {

        Object result = repository.getGuestbookWithWriter(100L);

        Object[] arr = (Object[])result;

        System.out.println("-------------------------------");
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testGetGuestbookWithReviewCount(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());
//        PageRequestDTO requestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(10)
//                .type("11")
//                .build();

//        List<Object[]> result = repository.getGuestbookWithReviewCount(requestDTO);
        Page<Object[]> result = repository.getGuestbookWithReviewCount(pageable);

        result.get().forEach(row->{
            Object[] arr = (Object[]) row;
            System.out.println(arr);
        });



        for(Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testGetGuestbookByGno(){
        Object result = repository.getGuestbookByGno(5L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    @DisplayName("게시판, 회원아이디, 댓글 수")
    @Test
    public void testSearch(){
        repository.search1();
    }

    @DisplayName("검색 조건")
    @Test
    public void testSearchPage(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending().and(Sort.by("title").ascending()));
        Page<Object[]> result = repository.searchPage("t","11",pageable);
    }
}