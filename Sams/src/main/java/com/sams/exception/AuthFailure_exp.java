package com.sams.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class AuthFailure_exp {
    public static ResponseEntity<Map<String, String>> passwordMatchFailed() {
        Map<String, String> response_body = new HashMap<>();
        response_body.put("message", "Password doesn't match.");
        return new ResponseEntity<> (response_body, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<Map<String, String>> invalidSessionKey() {
        Map<String, String> response_body = new HashMap<>();
        response_body.put("message", "Session key invalid.");
        return new ResponseEntity<> (response_body, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<Map<String, String>> invalidRoleAccount() {
        Map<String, String> response_body = new HashMap<>();
        response_body.put("message", "Assigner or assignee role invalid.");
        return new ResponseEntity<> (response_body, HttpStatus.UNAUTHORIZED);
    }
}
