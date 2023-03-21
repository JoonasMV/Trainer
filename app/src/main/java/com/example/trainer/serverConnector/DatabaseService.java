package com.example.trainer.serverConnector;

import java.util.List;

public interface DatabaseService<T> {
    List<T> getAll();
    T getById(String id);
    T save(T item);

}
