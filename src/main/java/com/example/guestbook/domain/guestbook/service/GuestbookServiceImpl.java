package com.example.guestbook.domain.guestbook.service;

import com.example.guestbook.domain.guestbook.dto.GuestbookDTO;
import com.example.guestbook.domain.guestbook.dto.GuestbookListAllDTO;
import com.example.guestbook.domain.guestbook.dto.GuestbookReadDTO;
import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.guestbook.repository.GuestbookRepository;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.review.repository.ReviewRepository;
import com.example.guestbook.global.page.PageRequestDTO;
import com.example.guestbook.global.page.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;
    private final ReviewRepository reviewRepository;

    @Override
    public Long register(GuestbookDTO guestbookDTO) {
        log.info(guestbookDTO);

        Guestbook entity = toEntity(guestbookDTO);

        repository.save(entity);

        log.info(entity.getGno());

        return entity.getGno();
    }

    @Override
    public GuestbookDTO get(Long gno) {
        Object result = repository.getGuestbookByGno(gno);

        Object[] arr = (Object[]) result;

        return toDTO((Guestbook) arr[0], (Member) arr[1], (Long) arr[2]);

    }

    @Override
    public GuestbookReadDTO readOne(Long gno) {
        Optional<Guestbook> result = repository.findByIdWithImage(gno);

        Guestbook guestbook = result.orElseThrow();

        GuestbookReadDTO readDTO = toReadDTO(guestbook);

        return readDTO;

    }

    public PageResponseDTO<GuestbookDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        log.info("(S-getList) pageRequestDTO : "+pageRequestDTO);

//        Function<Object[], BoardDTO> fn = (en->toDTO((Board)en[0], (Member) en[1], (Long)en[2]));
        Function<Object[], GuestbookDTO> fn = (en->
                toDTO((Guestbook) en[0], (Member) en[1], (Long) en[2]));

        Page<Object[]> result = repository.getGuestbookWithReviewCount(pageRequestDTO.getPageable("gno"));

//        Page<Object[]> result = repository.searchPage(
//                pageRequestDTO.getType(),
//                pageRequestDTO.getKeyword(),
////                pageRequestDTO.getPageable(Sort.by("bno").descending())
//                pageRequestDTO.getPageable("gno")
//        );
        return new PageResponseDTO<>(result, fn);
    }
//    @Override
//    public PageResponseDTO<GuestbookDTO, Object[]> getList(PageRequestDTO requestDTO){
//        log.info("RequestDTO : "+requestDTO);
//
//
//        Function<Object[], GuestbookDTO> fn = (en->toDTO((Guestbook) en[0], (Member) en[1], (Long) en[2]));
//
////        Page<Object[]> result = repository.getGuestbookWithReviewCount(
////                requestDTO.getPageable(Sort.by("gno").descending()));
//
//        Page<Object[]> result = repository.searchPage(
//                requestDTO.getType(),
//                requestDTO.getKeyword(),
//                requestDTO.getPageable("gno"));
//
//        return new PageResponseDTO<>(result, fn);
//    }

//    @Override
//    public void modify(GuestbookDTO guestbookDTO) {
//        Guestbook guestbook = repository.getReferenceById(guestbookDTO.getGno());
//
//        if(guestbook != null){
////            guestbook.changeContent(guestbook.getContent());
////            guestbook.changeContent(guestbook.getTitle());
//            guestbook.change(guestbook.getTitle(), guestbook.getContent());
//
//            repository.save(guestbook);
//        }
//    }
    @Override
    public void modify(GuestbookDTO  guestbookDTO){
        Optional<Guestbook> result = repository.findById(guestbookDTO.getGno());

        Guestbook guestbook = result.orElseThrow();

        guestbook.change(guestbook.getTitle(), guestbook.getContent());

        // 첨부파일 처리
        guestbook.clearImages();

        if(guestbookDTO.getImgNames() != null){
            for(String img : guestbookDTO.getImgNames()){
                String[] arr = img.split("_");
                guestbook.addImage(arr[0], arr[1]);
            }
        }
        repository.save(guestbook);
    }

    @Transactional
    @Override
    public void removeWithReviews(Long gno) {
        reviewRepository.deleteByGno(gno);

        repository.deleteById(gno);
    }

    @Override
    public void remove(Long gno){
        repository.deleteById(gno);
    }

    // 게시물 목록 처리
    @Override
    public PageResponseDTO<GuestbookListAllDTO, Object[]> listWithAll(PageRequestDTO requestDTO) {
        String[] types = requestDTO.getTypes();
        String keyword = requestDTO.getKeyword();
        Pageable pageable = requestDTO.getPageable("gno");

        Page<GuestbookListAllDTO> result = repository.searchWithAll(types, keyword, pageable);

        return null;
    }

}
