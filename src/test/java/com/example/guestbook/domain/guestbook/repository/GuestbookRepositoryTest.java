package com.example.guestbook.domain.guestbook.repository;

import com.example.guestbook.domain.guestbook.dto.GuestbookListAllDTO;
import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.image.entity.Images;
import com.example.guestbook.domain.image.repository.ImageRepository;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.member.repository.MemberRepository;
import com.example.guestbook.domain.review.repository.ReviewRepository;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
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
    @Autowired
    private ReviewRepository reviewRepository;

    @DisplayName("저장 테스트")
//    @Cascade(CascadeType.ALL)
    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{

            Guestbook guestbook = Guestbook.builder()
                    .title("(New) Title +++ "+i)
                    .content("Content +++ "+i)
                    .writer("user"+i)
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
    @Transactional
    @Test
    public void testSearchPage(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending().and(Sort.by("title").ascending()));
        Page<Object[]> result = repository.searchPage("t","11",pageable);
    }


    @DisplayName("Image 객체 관리")
    @Test
    public void testInsertImages(){

        Guestbook guestbook = Guestbook.builder()
                .title("Image Test")
                .content("첨부파일 테스트")
                .writer("user33")
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

    @DisplayName("EntityGraph와 조회 테스트")
    @Test
    public void testReadWithImage(){
        Optional<Guestbook> result = repository.findByIdWithImage(407L);

        Guestbook guestbook = result.orElseThrow();

        System.out.println(guestbook);
        System.out.println("-".repeat(30));

        for(Images images : guestbook.getImageSet()){
            System.out.println(images);
        }
    }

    @DisplayName("orphanRemoval 속성 테스트")
    @Transactional
    @Commit
    @Test
    public void testModifyImages(){
        Optional<Guestbook> result = repository.findByIdWithImage(407L);

        Guestbook guestbook = result.orElseThrow();
        System.out.println(guestbook);

        // 기존 첨부 파일 삭제
        guestbook.clearImages();

        // 새로운 첨부파일 등록
        for(int i=0; i<2; i++){
            guestbook.addImage(UUID.randomUUID().toString(), "newImage"+i+".jpeg");
        }
        repository.save(guestbook);
    }


    @DisplayName("게시물 및 첨부파일 삭제")
    @Test
    public void testRemoveAll(){

        Long gno = 307L;

        // todo 댓글 삭제
        reviewRepository.deleteByGuestbook_gno(gno);
    }

    @DisplayName("N+1 문제와 @BatchSize 확인")
    @Test
    public void testInsertAll(){
        IntStream.rangeClosed(1,100).forEach(i->{
//            Random random = new Random();
//            int firstNum = random.nextInt(1000,9999);
//            int secNum = random.nextInt(1000,9999);
//
//            Member member = Member.builder()
//                    .email("aa"+i+"@aa.com")
//                    .pwd("1111")
//                    .nickname("user"+i)
//                    .phone("010-"+firstNum+"-"+secNum)
//                    .build();
//
//            memberRepository.save(member);

            Guestbook guestbook = Guestbook.builder()
                    .title("Batch Test "+i)
                    .content(" ++ Content ++"+i)
                    .writer("user"+i)
                    .build();

            for(int j=0; j<3; j++){
                if(i%5 == 0){
                    continue;
                }
                guestbook.addImage(UUID.randomUUID().toString(), i+"file"+j+".jpg");
            }
            repository.save(guestbook);
        });
    }

    @DisplayName("N+1 처리 테스트")
    @Test
    @Transactional
    public void testSearchImageReviewCount(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());

        repository.searchWithAll(null, null, pageable);
    }


    @DisplayName("Querydsl 튜플 처리")
    @Transactional
    @Test
    public void testSearchImageReviewCnt(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());

        Page<GuestbookListAllDTO> result = repository.searchWithAll(null, null, pageable);

        System.out.println(result.getTotalPages());

        result.getContent().forEach(System.out::println);
    }

}