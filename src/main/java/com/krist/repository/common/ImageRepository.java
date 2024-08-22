package com.krist.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krist.entity.common.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
