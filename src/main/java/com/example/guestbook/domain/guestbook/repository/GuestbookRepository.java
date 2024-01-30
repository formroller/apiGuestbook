package com.example.guestbook.domain.guestbook.repository;

import com.example.guestbook.domain.guestbook.entity.Guestbook;
import com.example.guestbook.domain.guestbook.repository.search.SearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> , SearchRepository {

    // 목록 - 게시글 번호, 제목, 댓글 갯수, 작성자이름/이메일
//    @Query("select g, w from Guestbook g left join g.writer w where g.gno = :gno")
//    Object getGuestbookWithWriter(@Param("gno") Long gno);
    @Query("select g, w from Guestbook g left join g.writer w where g.gno =:gno")
    Object getGuestbookWithWriter(@Param("gno")Long gno);
//
//    // 조회 - 게시글 번호, 제목, 내용, 댓글 갯수, 작성자명/이메일
//    @Query(value = "select g, r, count (r) from Guestbook g left join Review r on r.guestbook = g where g.gno=:gno group by r",
//    countQuery = "select count(g) from Guestbook  g")
    @Query(value = "select g, w, count(r) " +
            "from Guestbook g " +
            "left join g.writer w " +
            "left join Review r on r.guestbook = g " +
            "group by g",
    countQuery = "select count (g) from Guestbook g")
    Page<Object[]> getGuestbookWithReviewCount(Pageable pageable);


    // 조회 화면 구성 - board, member + review
    @Query("select g, w, count(r) " +
            "from Guestbook g " +
            "left join g.writer w " +
            "left join Review r on r.guestbook = g " +
            "where g.gno =:gno")
    Object getGuestbookByGno(@Param("gno") Long gno);

    @Modifying
    @Query("delete from Review r where r.guestbook.gno =:gno")
    void deleteByGno(@Param("gno") Long gno);
}
