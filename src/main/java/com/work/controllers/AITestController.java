package com.work.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AITestController {

    private final ChatClient chatClient;

    public AITestController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/getTextMessage")
    public ResponseEntity<String> getTextMessage() {
        return new ResponseEntity("Hi Kishor", HttpStatus.OK);
    }

    //http://localhost:8080/ai/chat?message=What is Spring AI?
    @GetMapping("/ai/chat")
    public String generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        System.out.println("Kishor::: message: " + message);
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/getPopulation/{countryName}")
    public String getPopulation(@PathVariable("countryName") String countryName) {
        if(!countryName.isBlank() &&  !countryName.isEmpty()) {
            String promptText = "What is the current population of "+countryName+"? Respond with only the number, with no additional text, words, or formatting.";

            OllamaOptions options = OllamaOptions.builder()
                    .temperature(0.0d)
                    .topP(1.0d)
                    .seed(42)
                    .build();

            String population = chatClient.prompt()
                    .user(promptText)
                    .options(options)
                    .call()
                    .content();
            return population;
        }
        return "Country Name is blank";
    }
}
