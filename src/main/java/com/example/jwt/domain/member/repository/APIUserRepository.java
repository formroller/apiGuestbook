package com.example.jwt.domain.member.repository;

import com.example.jwt.domain.member.entity.APIUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APIUserRepository extends JpaRepository<APIUser, String> {

}
