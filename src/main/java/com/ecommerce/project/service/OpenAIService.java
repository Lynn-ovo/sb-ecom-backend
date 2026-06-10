package com.ecommerce.project.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OpenAIService {

    private final OpenAIClient client;
    private final String apiKey;

    public OpenAIService(@Value("${openai.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    public String askGPT(String systemPrompt, String userPrompt) {

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4_1_MINI)
                .addSystemMessage(systemPrompt)
                .addUserMessage(userPrompt)
                .build();

        ChatCompletion completion = client.chat().completions().create(params);

        return completion.choices()
                .get(0)
                .message()
                .content()
                .orElse("Sorry, I could not generate a response.");
    }

    public String extractSearchKeyword(String userMessage) {

        String systemPrompt = """
                You extract the best product search keyword from a user's shopping request.

                Reply with only ONE short keyword or product type.
                Do not explain.
                Do not return a sentence.
                Do not include punctuation.

                Examples:
                "I need a cheap laptop for university" -> laptop
                "I want wireless headphones" -> headphones
                "Recommend an iPhone" -> iphone
                "I need a programming book" -> book
                "I want something for gaming" -> gaming
                "I need a phone for taking photos" -> phone
                "I want comfortable clothes" -> clothing
                """;

        String userPrompt = """
                User request:
                %s

                Return only the best search keyword.
                """.formatted(userMessage);

        String keyword = askGPT(systemPrompt, userPrompt)
                .trim()
                .toLowerCase()
                .replace("\"", "")
                .replace(".", "")
                .replace(",", "")
                .replace(":", "")
                .replace(";", "");

        System.out.println("Extracted keyword from OpenAI = " + keyword);

        return keyword;
    }

    // ===== 新增：图片识别关键词 =====
    public String extractKeywordFromImage(String base64Image, String mimeType) {
        try {
            String requestBody = """
                {
                  "model": "gpt-4o",
                  "messages": [
                    {
                      "role": "user",
                      "content": [
                        {
                          "type": "image_url",
                          "image_url": {
                            "url": "data:%s;base64,%s"
                          }
                        },
                        {
                          "type": "text",
                          "text": "Look at this image and identify the main product. Reply with only ONE short search keyword. Do not explain. Examples: laptop, headphones, phone, shoes, shirt"
                        }
                      ]
                    }
                  ],
                  "max_tokens": 20
                }
                """.formatted(mimeType, base64Image);

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            String body = httpResponse.body();
            System.out.println("Vision API response: " + body);

            // 用正则解析，不受空格影响
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\"content\":\\s*\"([^\"]+)\"");
            java.util.regex.Matcher matcher = pattern.matcher(body);
            String keyword = "product";
            if (matcher.find()) {
                keyword = matcher.group(1)
                        .trim()
                        .toLowerCase()
                        .replaceAll("[^a-z0-9 ]", "")
                        .trim();
            }

            System.out.println("Image keyword from OpenAI = " + keyword);
            return keyword;

        } catch (Exception e) {
            System.out.println("Image recognition error: " + e.getMessage());
            return "product";
        }
    }
}