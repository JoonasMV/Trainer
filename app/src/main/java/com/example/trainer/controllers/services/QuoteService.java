package com.example.trainer.controllers.services;

import com.example.trainer.api.QuoteOperations;

import java.util.List;

public class QuoteService {

    private final QuoteOperations api;

    public QuoteService(QuoteOperations api) {
        this.api = api;
    }

    public List<String> getQuotes() {
        return api.getQuotes();
    }
}
