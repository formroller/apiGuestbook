package com.example.guestbook.domain.guestbook.repository;

import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.image.repository.ImageRepository;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.repository.MemberRepository;
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
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
class GuestbookRepositoryTest {
    @Autowired
    private GuestbookRepository repository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("저장 테스트")
    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{

            Member member = Member.builder().email("aa"+i+"@aa.com").build();
//            Member member = Member.builder().mno((long)i).email().build();

//            Long mno = (long) i;
//            Member member = memberRepository.findById(mno).get();

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


    @DisplayName("Image 객체 관리")
    @Test
    public void testInsertImages(){

        Member member = Member.builder().email("aa44@aa.com").build();
        Guestbook guestbook = Guestbook.builder()
                .title("Image Test")
                .content("첨부파일 테스트")
                .writer(member)
                .build();

        for(int i=0; i<3; i++){
            guestbook.addImage(UUID.randomUUID().toString(), "file"+i+".jpeg");
            System.out.println(guestbook);
        }// end for
        repository.save(guestbook);
    }

    @DisplayName("Read Image")
    @Test
    @Transactional
    public void testReadImages(){
        Optional<Guestbook> result = repository.findById(101L);

        Guestbook guestbook = result.orElseThrow();

        System.out.println(result);
        System.out.println(guestbook);
        System.out.println("-".repeat(40));
        System.out.println(guestbook.getImageSet());
    }
}