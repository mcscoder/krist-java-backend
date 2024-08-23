package com.krist.controller.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.krist.entity.common.Image;
import com.krist.exception.custom.BadRequestException;
import com.krist.repository.common.ImageRepository;

@RestController
@RequestMapping("/public/upload")
public class UploadController {

    private static final String UPLOAD_DIR = "uploads/";

    private final ImageRepository imageRepository;

    public UploadController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostMapping("/images")
    public ResponseEntity<Object> uploadImages(@RequestParam("files") MultipartFile[] files) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath();
            List<Image> images = new ArrayList<>();

            // Create the directory if it does not exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Process each file
            for (MultipartFile file : files) {
                String filename = Instant.now().toEpochMilli() + "-" + file.getOriginalFilename();

                if (filename != null) {
                    Path filePath = uploadPath.resolve(filename);

                    file.transferTo(filePath.toFile());
                    images.add(new Image("/public/images/" + filename));
                }
            }

            List<Image> savedImages = imageRepository.saveAll(images);

            return ResponseEntity.ok().body(savedImages);
        } catch (IllegalStateException | IOException e) {
            throw new BadRequestException("Your request is bullshit so we would not handle it");
        }
    }
}
