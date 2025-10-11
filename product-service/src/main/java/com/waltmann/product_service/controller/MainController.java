package com.waltmann.product_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class MainController {
    @GetMapping
    public ResponseEntity<Map<String, String>> index() {
        Map<String, String> map = new HashMap<>();

        map.put("message", "API is running at 8080 port");

        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }
}
