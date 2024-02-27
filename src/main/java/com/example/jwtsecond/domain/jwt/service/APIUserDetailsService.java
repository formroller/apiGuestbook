package com.example.jwtsecond.domain.jwt.service;

import com.example.jwtsecond.domain.jwt.dto.APIUserDTO;
import com.example.jwtsecond.domain.jwt.entity.APIUser;
import com.example.jwtsecond.domain.jwt.repository.APIUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class APIUserDetailsService implements UserDetailsService {

    private final APIUserRepository apiUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<APIUser> result = apiUserRepository.findById(username);

        APIUser apiUser = result.orElseThrow(() -> new UsernameNotFoundException("Cannot Find MID"));

        log.info(" ---------------------------- APIUserDetailsService apiUser ----------------------------");

        return null;
//        APIUserDTO dto = new APIUserDTO(
//                apiUser.getMid(),
//                apiUser.getPwd(),
//                List.of(new SimpleGrantedAuthority("ROLE_USER")));
//
//        log.info(" DTO : "+dto);
//        return dto;
    }
}
