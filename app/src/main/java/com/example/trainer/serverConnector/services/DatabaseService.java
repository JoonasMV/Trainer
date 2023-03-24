package com.example.trainer.serverConnector.services;

import java.util.List;

public interface DatabaseService<T> {
    List<T> getAll();
    T getById(String id);
    T save(T item);
    T getByName(String name);

}
