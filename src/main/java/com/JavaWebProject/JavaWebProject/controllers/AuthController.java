package com.JavaWebProject.JavaWebProject.controllers;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope(scopeName = "session")
@RequestMapping("/auth")
public class AuthController {
    private String role;
    private String username;
    private String message;
    
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin() {
        return "AuthPage/login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody Map<String, Object> data) {
        System.out.println(data.get("username"));
        System.out.println(data.get("password"));
        return "AuthPage/login";
    }
    
    private String hash(String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (Exception e) {
            return null;
        }
        BigInteger number = new BigInteger(1, md.digest(str.getBytes(StandardCharsets.UTF_8)));
        StringBuilder hex = new StringBuilder(number.toString(16));
        while (hex.length() < 64) {
            hex.insert(0, '0');
        }
        return hex.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getRole() {
        return role;
    }
}
