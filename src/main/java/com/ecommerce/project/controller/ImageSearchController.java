package com.ecommerce.project.controller;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.OpenAIService;
import com.ecommerce.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/image-search")
@RequiredArgsConstructor
public class ImageSearchController {

    private final ProductService productService;
    private final OpenAIService openAIService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> searchByImage(@RequestParam("image") MultipartFile image) {
        try {
            String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
            String mimeType = image.getContentType();

            String keyword = openAIService.extractKeywordFromImage(base64Image, mimeType);
            System.out.println("Image keyword = " + keyword);

            ProductResponse products;
            try {
                products = productService.searchProductByKeyword(
                        keyword, 0, 50, "specialPrice", "asc"
                );
            } catch (Exception e) {
                products = new ProductResponse();
                products.setContent(List.of());
            }

            StringBuilder productInfo = new StringBuilder();
            if (products.getContent().isEmpty()) {
                productInfo.append("No matching products found.");
            } else {
                for (ProductDTO product : products.getContent()) {
                    productInfo.append("- Product Name: ").append(product.getProductName()).append("\n")
                            .append("  Price: €").append(product.getSpecialPrice()).append("\n")
                            .append("  Description: ").append(product.getDescription()).append("\n\n");
                }
            }

            String systemPrompt = """
                    You are an AI shopping assistant for an English e-commerce website.
                    Only recommend products from the provided product list.
                    Never invent products.
                    Keep the answer friendly, concise, and useful.
                    Answer in English.
                    """;

            String userPrompt = """
                    The user uploaded an image of a: %s

                    Matching products from database:
                    %s

                    Task:
                    Recommend the most suitable product or products based on the image.
                    If no matching product exists, politely say the store does not currently have a suitable product.
                    """.formatted(keyword, productInfo.toString());

            String aiReply = openAIService.askGPT(systemPrompt, userPrompt);

            Map<String, Object> response = new HashMap<>();
            response.put("keyword", keyword);
            response.put("reply", aiReply);
            response.put("products", products.getContent());
            return response;

        } catch (Exception e) {
            System.out.println("Image search error: " + e.getMessage());
            Map<String, Object> error = new HashMap<>();
            error.put("reply", "Sorry, I could not process the image. Please try again.");
            error.put("products", List.of());
            return error;
        }
    }
}