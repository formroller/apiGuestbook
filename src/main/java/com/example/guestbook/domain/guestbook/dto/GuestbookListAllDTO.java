package com.example.guestbook.domain.guestbook.dto;

import com.example.guestbook.domain.image.dto.ImageResultDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuestbookListAllDTO {
    private Long gno;
    private String title;
    private String writer;
    private LocalDateTime regDate;
    private Long reviewCnt;
    private List<ImageResultDTO> guestbookImages;
}
