package com.example.trainer.controllers.services;

import com.example.trainer.api.QuoteOperations;

import java.util.List;

/**
 * handles quote fetching from api
 */
public class QuoteService {

    private final QuoteOperations api;

    private String quote = null;

    public QuoteService(QuoteOperations api) {
        this.api = api;
    }

    /**
     * gets quote from api if quote is not yet fetched
     * @return String guote
     */
    public String getQuotes() {
        if(quote == null){
            quote = api.getQuotes();
            return quote;
        }
        return quote;
    }
}
