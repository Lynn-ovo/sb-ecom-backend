package com.ecommerce.project.controller;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.OpenAIService;
import com.ecommerce.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatbotController {

    private final ProductService productService;
    private final OpenAIService openAIService;

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> request) {

        String message = request.get("message");

        String keyword = openAIService.extractSearchKeyword(message);

        System.out.println("User message = " + message);
        System.out.println("Search keyword = " + keyword);

        ProductResponse products = productService.searchProductByKeyword(
                keyword,
                0,
                50,
                "specialPrice",
                "asc"
        );

        StringBuilder productInfo = new StringBuilder();

        if (products.getContent().isEmpty()) {
            productInfo.append("No matching products found.");
        } else {
            for (ProductDTO product : products.getContent()) {
                productInfo.append("- Product Name: ")
                        .append(product.getProductName())
                        .append("\n")
                        .append("  Price: €")
                        .append(product.getSpecialPrice())
                        .append("\n")
                        .append("  Description: ")
                        .append(product.getDescription())
                        .append("\n\n");
            }
        }

        String systemPrompt = """
                You are an AI shopping assistant for an English e-commerce website.
                Only recommend products from the provided product list.
                Never invent products.
                Do not recommend unrelated products.
                Keep the answer friendly, concise, and useful.
                Answer in English.
                """;

        String userPrompt = """
                User request:
                %s

                Search keyword used:
                %s

                Matching products from database:
                %s

                Task:
                Recommend the most suitable product or products.
                If the user wants cheap, affordable, budget-friendly, or student-friendly products, prioritize lower prices.
                If the user wants programming, study, college, university, or work use, prioritize practical and reliable products.
                If the user wants large storage, prioritize descriptions mentioning SSD, storage, 512GB, 1TB, or similar.
                If no matching product exists, politely say the store does not currently have a suitable product.
                """
                .formatted(message, keyword, productInfo.toString());

        String aiReply = openAIService.askGPT(systemPrompt, userPrompt);

        Map<String, String> response = new HashMap<>();
        response.put("reply", aiReply);

        return response;
    }
}