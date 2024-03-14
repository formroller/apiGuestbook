package com.example.guestbook.domain.member.repository;

import com.example.guestbook.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
