package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Apis {
    @GetMapping("/")
    String newEmployee() {
        return "Hello World!";
    }
}
