package com.example.trainer.controllers;

public interface Callback<T> {

    void onComplete(T result);
}
