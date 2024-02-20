package com.example.jwt.security.service;

import com.example.jwt.domain.member.entity.APIUser;
import com.example.jwt.domain.member.repository.APIUserRepository;
import com.example.jwt.security.dto.APIUserDTO;
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

        APIUser apiUser = result.orElseThrow(() -> new UsernameNotFoundException("Cannot find mid"));

        log.info("APIUserDetailsService apiuUer ----------------------");

        APIUserDTO dto = new APIUserDTO(
                apiUser.getMid(),
                apiUser.getPwd(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        log.info(dto);

        return dto;
    }
}
