package com.example.trainer.api.responses;

/**
 * Represents a response that contains a JWT
 */
public class Token {
    private final String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
