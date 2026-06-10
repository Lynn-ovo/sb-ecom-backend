package com.ecommerce.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class AiImageServiceImpl implements AiImageService {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String recognizeImage(MultipartFile image) throws Exception {

        String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
        String mimeType = image.getContentType();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");

        List<Map<String, Object>> input = new ArrayList<>();

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");

        List<Map<String, Object>> content = new ArrayList<>();

        Map<String, Object> textContent = new HashMap<>();
        textContent.put("type", "input_text");
        textContent.put("text",
                "Analyze this product image for an e-commerce website. " +
                        "Return only JSON with: category, product_type, brand_if_visible, color, keywords, search_query.");

        Map<String, Object> imageContent = new HashMap<>();
        imageContent.put("type", "input_image");
        imageContent.put("image_url", "data:" + mimeType + ";base64," + base64Image);

        content.add(textContent);
        content.add(imageContent);

        message.put("content", content);
        input.add(message);

        requestBody.put("input", input);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.openai.com/v1/responses",
                HttpMethod.POST,
                entity,
                Map.class
        );

        return objectMapper.writeValueAsString(response.getBody());
    }
}