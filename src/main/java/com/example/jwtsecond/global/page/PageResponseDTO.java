package com.example.jwtsecond.global.page;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponseDTO<E> {

    private int page;
    private int size;
    private int total;

    // 시작 페이지, 마지막 페이지
    private int start;
    private int end;

    // 이전-다음 페이지 여부
    private boolean prev;
    private boolean next;

    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO requestDTO, List<E> dtoList, int total){

        if(total <= 0){
            return;
        }

        this.page = requestDTO.getPage();
        this.size = requestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page / 10.0))*10;
        this.start = this.end - 9;

        int last = (int)(Math.ceil(total/(double)size));

        this.end = end > last ? last : end;
        this.prev = this.start>1;

        this.next = total > this.end * this.size;
    }
}
