package com.example.guestbook.domain.image.repository;

import com.example.guestbook.domain.image.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Images, Long> {

}
