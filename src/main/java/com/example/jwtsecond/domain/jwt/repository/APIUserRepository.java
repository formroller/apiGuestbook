package com.example.jwtsecond.domain.jwt.repository;

import com.example.jwtsecond.domain.jwt.entity.APIUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APIUserRepository extends JpaRepository<APIUser, String> {

}
