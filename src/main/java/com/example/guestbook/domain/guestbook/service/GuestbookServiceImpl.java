package com.example.guestbook.domain.guestbook.service;

import com.example.guestbook.domain.guestbook.dto.GuestbookDTO;
import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.guestbook.repository.GuestbookRepository;
import com.example.guestbook.domain.member.entity.Member;
import com.example.guestbook.domain.review.repository.ReviewRepository;
import com.example.guestbook.global.page.PageRequestDTO;
import com.example.guestbook.global.page.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;
    private final ReviewRepository reviewRepository;

    @Override
    public Long register(GuestbookDTO guestbookDTO) {
        log.info(guestbookDTO);

        Guestbook entity = toEntity(guestbookDTO);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public GuestbookDTO get(Long gno) {
        Object result = repository.getGuestbookByGno(gno);

        Object[] arr = (Object[]) result;

        return toDTO((Guestbook) arr[0], (Member) arr[1], (Long) arr[2]);

    }


    @Override
    public PageResponseDTO<GuestbookDTO,Object[]> getList(PageRequestDTO requestDTO){
        log.info("RequestDTO : "+requestDTO);
        Function<Object[], GuestbookDTO> fn = (en->toDTO((Guestbook) en[0], (Member) en[1], (Long) en[2]));

//        Page<Object[]> result = repository.getGuestbookWithReviewCount(
//                requestDTO.getPageable(Sort.by("gno").descending()));

        Page<Object[]> result = repository.searchPage(
                requestDTO.getType(),
                requestDTO.getKeyword(),
                requestDTO.getPageable(Sort.by("gno").descending()));

        return new PageResponseDTO<>(result, fn);
    }

    @Override
    public void modify(GuestbookDTO guestbookDTO) {
        Guestbook guestbook = repository.getReferenceById(guestbookDTO.getGno());

        if(guestbook != null){
//            guestbook.changeContent(guestbook.getContent());
//            guestbook.changeContent(guestbook.getTitle());
            guestbook.change(guestbook.getTitle(), guestbook.getContent());

            repository.save(guestbook);
        }


    }

    @Transactional
    @Override
    public void removeWithReviews(Long gno) {
        reviewRepository.deleteByGno(gno);

        repository.deleteById(gno);
    }

}
