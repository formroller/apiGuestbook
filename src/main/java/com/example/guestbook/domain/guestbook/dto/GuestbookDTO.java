package com.example.guestbook.domain.guestbook.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestbookDTO {
    private Long gno;
    private String title;
    private String content;
    private LocalDateTime regDate, modDate;

    private String writer;
    // member
    private String writerEmail;
    private String writerName;

    // review
    private int reviewCnt;


    // 첨부파일명
    private List<String> imgNames;


}
