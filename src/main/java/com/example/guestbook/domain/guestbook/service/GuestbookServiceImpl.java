package com.example.guestbook.domain.guestbook.service;

import com.example.guestbook.domain.guestbook.dto.GuestbookDTO;
import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.guestbook.entity.QGuestbook;
import com.example.guestbook.domain.guestbook.repository.GuestbookRepository;
import com.example.guestbook.global.page.PageRequestDTO;
import com.example.guestbook.global.page.PageResponseDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO guestbookDTO) {
        log.info(guestbookDTO);

        Guestbook entity = toEntity(guestbookDTO);

        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent() ? toDTO(result.get()) : null;
    }

    @Override
    public PageResponseDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

//        BooleanBuilder builder = getSearch(requestDTO);

//        Page<Guestbook> result = repository.findAll(builder, pageable);
        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> toDTO(entity));

        return new PageResponseDTO<>(result, fn);
    }

    @Override
    public PageResponseDTO<GuestbookDTO, Guestbook> getListSecond(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); //검색 조건 처리

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable); //Querydsl 사용

        Function<Guestbook, GuestbookDTO> fn = (entity -> toDTO(entity));

        return new PageResponseDTO<>(result, fn );
    }

    @Override
    public void modify(GuestbookDTO guestbookDTO) {
        Guestbook guestbook = repository.getReferenceById(guestbookDTO.getGno());

        if(guestbook != null){
            guestbook.changeContent(guestbook.getContent());
            guestbook.changeContent(guestbook.getTitle());

            repository.save(guestbook);
        }


    }

    @Override
    public void remove(Long gno) {
//        guestbookRepository.deleteById(gno);
        Long num = repository.findById(gno).orElseThrow().getGno();

        repository.deleteById(num);


    }


    /* Querydsl 처리 */
//    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
//
//        String type = requestDTO.getType();
//
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//
//        QGuestbook qGuestbook = QGuestbook.guestbook;
//
//        String keyword = requestDTO.getKeyword();
//
//        BooleanExpression expression = qGuestbook.gno.gt(0L); // gno > 0 조건만 생성
//
//        booleanBuilder.and(expression);
//
//        if(type == null || type.trim().length() == 0){ //검색 조건이 없는 경우
//            return booleanBuilder;
//        }
//
//
//        //검색 조건을 작성하기
//        BooleanBuilder conditionBuilder = new BooleanBuilder();
//
//        if(type.contains("t")){
//            conditionBuilder.or(qGuestbook.title.contains(keyword));
//        }
//        if(type.contains("c")){
//            conditionBuilder.or(qGuestbook.content.contains(keyword));
//        }
//        if(type.contains("w")){
//            conditionBuilder.or(qGuestbook.writer.contains(keyword));
//        }
//
//        //모든 조건 통합
//        booleanBuilder.and(conditionBuilder);
//
//        return booleanBuilder;
//    }
    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = qGuestbook.gno.gt(0L);

        booleanBuilder.and(expression);

        if(type == null || type.trim().length()==0){
            return booleanBuilder;
        }


        String[] typeArr = type.split("");

        BooleanBuilder conditionalBuilder = new BooleanBuilder();

        for(String t:typeArr){
            switch (t){
                case "t" -> conditionalBuilder.or(qGuestbook.title.contains(keyword));
                case "c" -> conditionalBuilder.or(qGuestbook.content.contains(keyword));
                case "w" -> conditionalBuilder.or(qGuestbook.writer.contains(keyword));
            }
            booleanBuilder.and(conditionalBuilder);
        }
        return booleanBuilder;
    }
}
