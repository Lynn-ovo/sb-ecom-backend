package com.ecommerce.project.controller;

import com.ecommerce.project.service.AiImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ai")
public class AiImageController {

    private final AiImageService aiImageService;

    public AiImageController(AiImageService aiImageService) {
        this.aiImageService = aiImageService;
    }

    @PostMapping("/image-recognition")
    public ResponseEntity<String> recognizeImage(@RequestParam("image") MultipartFile image) {
        try {
            String result = aiImageService.recognizeImage(image);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("AI image recognition failed: " + e.getMessage());
        }
    }
}