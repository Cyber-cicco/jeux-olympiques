package org.example.dao;

import java.util.HashMap;
import java.util.Map;

public class Cache<T> {
    private Map<String, T> cache = new HashMap<>();

    public Map<String, T> getCache() {
        return cache;
    }
}
