package com.work.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

        //return "kishor";
    }
}
