package com.ecommerce.project.service;

import org.springframework.web.multipart.MultipartFile;

public interface AiImageService {
    String recognizeImage(MultipartFile image) throws Exception;
}