package com.example.guestbook.domain.guestbook.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GuestbookDTO {
    private Long gno;
    private String title;
    private String content;
    private LocalDateTime regDate, modDate;

    // member
    private String writerEmail;
    private String writerName;

    // review
    private int reviewCnt;



}
