package com.rrs.spring_site.controllers;

import com.rrs.spring_site.models.Photo;
import com.rrs.spring_site.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
public class PhotoController {

    private final ImageRepository imageRepository;

    @GetMapping("/photos/{id}")
    private ResponseEntity<?> getPhoto(@PathVariable Long id) {
    Photo photo = imageRepository.findById(id).orElse(null);
        assert photo != null;
        return ResponseEntity.ok()
            .header("fileName", photo.getOriginalFileName())
            .contentType(MediaType.valueOf(photo.getContentType()))
            .contentLength(photo.getSize())
            .body(new InputStreamResource(new ByteArrayInputStream(photo.getBytes())));

    }

}
