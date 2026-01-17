package com.work.controllers;

import com.google.gson.Gson;
import com.work.service.MyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
public class AITestController {

    @Autowired
    private MyServices myServices;

    @GetMapping("/getTextMessage")
    public ResponseEntity<String> getTextMessage() {
        List<String> messages = new ArrayList<>();
        messages.add("India");
        messages.add("USA");
        messages.add("UK");
        messages.add("Canada");
        Gson gson = new Gson();
        System.out.println("Messages:::" + gson.toJson(messages));
        return new ResponseEntity("Hi Kishor", HttpStatus.OK);
    }

    //http://localhost:8080/ai/chat?message=What is Spring AI?
    @GetMapping("/ai/chat")
    public String generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        System.out.println("Kishor::: message: " + message);
        return myServices.getAIMessage(message);
    }

    @GetMapping("/getPopulation/{countryName}")
    public String getPopulation(@PathVariable("countryName") String countryName) {
        return myServices.getPopulation(countryName);
    }

    @GetMapping("/getAsyncMessage")
    public ResponseEntity<List<String>> getAsyncMessage() {
        try {
            System.out.println("asynchronously rest method getAsyncMessage=" + Thread.currentThread().getName());
            List<CompletableFuture<String>> futuresList = List.of(
                    myServices.getWelcomeMessage(),
                    myServices.getWelcomeMessage(),
                    myServices.getWelcomeMessage(),
                    myServices.getWelcomeMessage(),
                    myServices.getWelcomeMessage()
            );
            CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[0])).join();
            List<String> results = futuresList.stream().map(CompletableFuture::join).toList();
            return new ResponseEntity(results, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity("Error Occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/getPopulationForCountries")
    public ResponseEntity<List<String>> getPopulationForCountries(
            @RequestParam("countryNames") List<String> countryNames) {
        try {
            System.out.println("asynchronously rest method getAsyncMessage=" + Thread.currentThread().getName());
            List<CompletableFuture<String>> futuresList = countryNames.stream().map(x ->{
                return myServices.getAIPopulation(x);
            }).collect(Collectors.toUnmodifiableList());
            CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[0])).join();
            List<String> results = futuresList.stream().map(CompletableFuture::join).toList();
            return new ResponseEntity(results, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity("Error Occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
