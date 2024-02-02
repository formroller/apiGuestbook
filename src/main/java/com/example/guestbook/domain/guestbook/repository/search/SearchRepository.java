package com.example.guestbook.domain.guestbook.repository.search;

import com.example.guestbook.domain.guestbook.dto.GuestbookListAllDTO;
import com.example.guestbook.domain.guestbook.dto.GuestbookListReviewCountDTO;
import com.example.guestbook.domain.guestbook.entity.Guestbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchRepository {
    Guestbook search1();

    // 검색 페이지 처리
    Page<Object[]> searchPage(String type,String keyword, Pageable pageable);

    Page<GuestbookListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable);

}
