package com.example.trainer.diagram.api.controllers.services;

import com.example.trainer.diagram.api.QuoteOperations;

/**
 * Handles quote fetching from api. Quote is cached in memory once it has been fetched.
 */
public class QuoteService {

    private final QuoteOperations api;

    private String quote = null;

    public QuoteService(QuoteOperations api) {
        this.api = api;
    }

    /**
     * Gets quote from api if quote is not yet fetched
     * @return String guote
     */
    public String getQuotes() {
        if(quote == null){
            quote = api.getQuotes();
        }
        return quote;
    }
}
