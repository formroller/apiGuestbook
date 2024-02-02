package com.example.guestbook.domain.guestbook.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GuestbookListReviewCountDTO {
    private Long gno;
    private String title;
    private String writer;
    private LocalDateTime reDate;
    private Long reviewCnt;
}
