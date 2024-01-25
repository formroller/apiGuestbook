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
    private String writerEmail;
    private LocalDateTime regDate, modDate;
    private int replyCnt;

}
