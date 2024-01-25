package com.example.guestbook.global.page;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<DTO,EN> {
    // DTO 리스트
    private List<DTO> dtoList;

    private int totalPage; // 총 페이지 번호
    private int page; // 현재 페이지
    private int size; // 목록 크기

    private int start, end; // 시작, 끝
    private boolean prev, next; // 이전, 다음

    private List<Integer> pageList; // pagination

    public PageResponseDTO(Page<EN> result, Function<EN, DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void  makePageList(Pageable pageable){

        this.page = pageable.getPageNumber()+1;
        this.size = pageable.getPageSize();

        // temp end page
        int tempEnd = (int)(Math.ceil(page/10.0))*10;

        start = tempEnd - 9;

        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}