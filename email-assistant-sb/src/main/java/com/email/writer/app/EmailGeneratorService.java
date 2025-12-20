package com.email.writer.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailGeneratorService {

    private final WebClient webClient;


    @Value("${groq.api.url}")
    private String groqApiUrl;
    @Value("${groq.api.key}")
    private String groqApiKey;

    public EmailGeneratorService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build(); // building the object of web client
    }


    public String generateEmailReply(EmailRequest emailRequest){

        // Step:1 Build the prompt
        String prompt = buildPrompt(emailRequest);

        // Step:2 Craft a request
        Map<String, Object> requestBody = new HashMap<>();
            // Model
        requestBody.put("model", "llama-3.1-8b-instant");
            // Messages (List of Maps)
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.add(userMessage);
            // Add messages to request body
        requestBody.put("messages", messages);

        // Step:3 Do request and get response
//        String response = webClient.post()
//                .uri(groqApiUrl+groqApiKey)
//                .header("Content-Type", "application/json")
//                .bodyValue(requestBody)
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();

        // Above one is not working as URI of Groq is different from Gemini, here we don't have to give key in uri
        String response = webClient.post()
                .uri(groqApiUrl) // NO API KEY in URL
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + groqApiKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // To Check the error
//        String response = webClient.post()
//                .uri(groqApiUrl)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + groqApiKey)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .bodyValue(requestBody)
//                .retrieve()
//                .onStatus(HttpStatusCode::isError, clientResponse ->
//                        clientResponse.bodyToMono(String.class)
//                                .map(errorBody -> {
//                                    System.out.println("Groq Error Response: " + errorBody);
//                                    return new RuntimeException(errorBody);
//                                })
//                )
//                .bodyToMono(String.class)
//                .block();



        // Step:4 Extract response and Return
            // Extract only the text content response from the whole response
        return extractResponseContent(response);
    }

    private String extractResponseContent(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);

            return rootNode
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();
        } catch (Exception e) {
            return "Error processing request: " + e.getMessage();
        }
    }


    private String buildPrompt(EmailRequest emailRequest) {

        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate a professional email reply for the following email content. Please don't generate a subject line ");
            // not null and not empty -> means tone is present then use a "Sarcastic" tone
        if(emailRequest.getTone()!=null && !emailRequest.getTone().isEmpty()){
            prompt.append("Use a ").append(emailRequest.getTone()).append("tone.");
        }

        prompt.append("\n Original email: \n").append(emailRequest.getEmailContent());
        return prompt.toString();
    }
}
