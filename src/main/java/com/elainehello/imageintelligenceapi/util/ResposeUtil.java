package com.elainehello.imageintelligenceapi.util;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResposeUtil {

    private ResposeUtil () {}

    public static ResponseEntity<Map<String, String>> ok(String message) {
        Map<String, String> body = new HashMap<>();
        body.put("message", message);
        return ResponseEntity.ok(body);
    }
}
